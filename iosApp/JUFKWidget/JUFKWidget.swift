//
//  JUFKWidget.swift
//  JUFKWidget
//
//  Created by Mykhailo Dorokhin on 13/02/2026.
//

import WidgetKit
import SwiftUI

// Mirror Kotlin data model
struct YoutubeVideoWidget: Codable {
    let title: String
    let url: String
    let thumbnailUrl: String?
    let videoId: String
}

// Timeline entry
struct JUFKWidgetEntry: TimelineEntry {
    let date: Date
    let videos: [YoutubeVideoWidget]
}

// Timeline provider
struct JUFKWidgetProvider: TimelineProvider {
    func placeholder(in context: Context) -> JUFKWidgetEntry {
        JUFKWidgetEntry(date: Date(), videos: [])
    }

    func getSnapshot(in context: Context, completion: @escaping (JUFKWidgetEntry) -> Void) {
        let entry = loadVideosFromUserDefaults()
        completion(entry)
    }

    func getTimeline(in context: Context, completion: @escaping (Timeline<JUFKWidgetEntry>) -> Void) {
        let entry = loadVideosFromUserDefaults()

        // Refresh every hour
        let refreshDate = Calendar.current.date(byAdding: .hour, value: 1, to: Date())!
        let timeline = Timeline(entries: [entry], policy: .after(refreshDate))
        completion(timeline)
    }

    private func loadVideosFromUserDefaults() -> JUFKWidgetEntry {
        let sharedDefaults = UserDefaults(suiteName: "group.com.ifochka.jufk.widgets")
        if let jsonString = sharedDefaults?.string(forKey: "youtubeVideos"),
           let data = jsonString.data(using: .utf8),
           let videos = try? JSONDecoder().decode([YoutubeVideoWidget].self, from: data) {
            return JUFKWidgetEntry(date: Date(), videos: Array(videos.prefix(4)))
        }
        return JUFKWidgetEntry(date: Date(), videos: [])
    }
}

// Widget view
struct JUFKWidgetEntryView: View {
    var entry: JUFKWidgetEntry
    @Environment(\.widgetFamily) var family

    var body: some View {
        Group {
            if entry.videos.isEmpty {
                // Empty state
                VStack {
                    Image(systemName: "play.rectangle.fill")
                        .font(.largeTitle)
                        .foregroundColor(.gray)
                    Text("No videos yet")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    Text("Open app to load videos")
                        .font(.caption2)
                        .foregroundColor(.secondary)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else {
                // Video grid
                switch family {
                case .systemSmall:
                    SingleVideoView(video: entry.videos[0])
                case .systemMedium:
                    HStack(spacing: 4) {
                        ForEach(0..<min(2, entry.videos.count), id: \.self) { index in
                            VideoGridItem(video: entry.videos[index])
                        }
                    }
                case .systemLarge, .systemExtraLarge:
                    VStack(spacing: 4) {
                        ForEach(0..<2, id: \.self) { row in
                            HStack(spacing: 4) {
                                ForEach(0..<2, id: \.self) { col in
                                    let index = row * 2 + col
                                    if index < entry.videos.count {
                                        VideoGridItem(video: entry.videos[index])
                                    }
                                }
                            }
                        }
                    }
                default:
                    EmptyView()
                }
            }
        }
        .containerBackground(.fill.tertiary, for: .widget)
    }
}

struct SingleVideoView: View {
    let video: YoutubeVideoWidget

    var body: some View {
        Link(destination: URL(string: video.url)!) {
            ZStack(alignment: .bottom) {
                // Thumbnail
                AsyncImage(url: URL(string: video.thumbnailUrl ?? "")) { image in
                    image.resizable().scaledToFill()
                } placeholder: {
                    Color.purple.opacity(0.3)
                }

                // Play overlay
                Image(systemName: "play.circle.fill")
                    .font(.system(size: 40))
                    .foregroundColor(.white.opacity(0.9))
                    .shadow(radius: 4)

                // Title
                VStack {
                    Spacer()
                    Text(video.title)
                        .font(.caption)
                        .lineLimit(2)
                        .padding(6)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(Color.black.opacity(0.7))
                        .foregroundColor(.white)
                }
            }
            .cornerRadius(8)
            .clipped()
        }
    }
}

struct VideoGridItem: View {
    let video: YoutubeVideoWidget

    var body: some View {
        Link(destination: URL(string: video.url)!) {
            ZStack(alignment: .bottomLeading) {
                // Thumbnail
                AsyncImage(url: URL(string: video.thumbnailUrl ?? "")) { image in
                    image.resizable().scaledToFill()
                } placeholder: {
                    Color.purple.opacity(0.3)
                }

                // Play icon
                VStack {
                    Image(systemName: "play.circle.fill")
                        .font(.title2)
                        .foregroundColor(.white.opacity(0.9))
                        .shadow(radius: 2)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)

                // Title
                Text(video.title)
                    .font(.caption2)
                    .lineLimit(2)
                    .padding(4)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color.black.opacity(0.7))
                    .foregroundColor(.white)
            }
            .cornerRadius(6)
            .clipped()
        }
    }
}

// Widget definition
struct JUFKWidget: Widget {
    let kind: String = "JUFKWidget"

    var body: some WidgetConfiguration {
        StaticConfiguration(
            kind: kind,
            provider: JUFKWidgetProvider()
        ) { entry in
            JUFKWidgetEntryView(entry: entry)
        }
        .configurationDisplayName("JUFK Videos")
        .description("Latest videos from Just Use LovelyI see  Kotlin")
        .supportedFamilies([.systemSmall, .systemMedium, .systemLarge])
    }
}
