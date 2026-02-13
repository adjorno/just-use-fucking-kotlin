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
        print("🔷 Widget loading videos from UserDefaults")
        let sharedDefaults = UserDefaults(suiteName: "group.com.ifochka.jufk.widgets")

        if sharedDefaults == nil {
            print("❌ Failed to create UserDefaults with suite name")
            return JUFKWidgetEntry(date: Date(), videos: [])
        }

        if let jsonString = sharedDefaults?.string(forKey: "youtubeVideos") {
            print("🔷 Found JSON string: \(jsonString.prefix(200))")

            if let data = jsonString.data(using: .utf8) {
                if let videos = try? JSONDecoder().decode([YoutubeVideoWidget].self, from: data) {
                    print("🔷 Decoded \(videos.count) videos: \(videos.map { $0.title })")
                    print("🔷 Thumbnail URLs: \(videos.map { $0.thumbnailUrl ?? "nil" })")
                    return JUFKWidgetEntry(date: Date(), videos: Array(videos.prefix(4)))
                } else {
                    print("❌ Failed to decode JSON")
                }
            } else {
                print("❌ Failed to convert string to data")
            }
        } else {
            print("❌ No data found for key 'youtubeVideos'")
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
                VStack(spacing: 8) {
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
                // Video layouts optimized for widget shapes
                switch family {
                case .systemSmall:
                    SingleVideoView(video: entry.videos[0])
                        .padding(2)
                case .systemMedium:
                    // Show 2 videos side by side, optimized for medium widget
                    HStack(spacing: 6) {
                        ForEach(0..<min(2, entry.videos.count), id: \.self) { index in
                            MediumVideoItem(video: entry.videos[index])
                        }
                    }
                    .padding(4)
                case .systemLarge, .systemExtraLarge:
                    // Show 2 videos vertically, giving more space for descriptions
                    VStack(spacing: 8) {
                        ForEach(0..<min(2, entry.videos.count), id: \.self) { index in
                            LargeVideoItem(video: entry.videos[index])
                        }
                        Spacer(minLength: 0)
                    }
                    .padding(6)
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
            ZStack {
                // Thumbnail with proper aspect ratio
                GeometryReader { geometry in
                    if let containerURL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: "group.com.ifochka.jufk.widgets") {
                        let imagePath = containerURL.appendingPathComponent("\(video.videoId).jpg")
                        
                        if let uiImage = UIImage(contentsOfFile: imagePath.path) {
                            Image(uiImage: uiImage)
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                                .frame(width: geometry.size.width, height: geometry.size.height)
                                .clipped()
                        } else {
                            Rectangle()
                                .fill(LinearGradient(
                                    colors: [Color.purple.opacity(0.6), Color.blue.opacity(0.4)],
                                    startPoint: .topLeading,
                                    endPoint: .bottomTrailing
                                ))
                                .overlay(
                                    Image(systemName: "photo")
                                        .font(.largeTitle)
                                        .foregroundColor(.white.opacity(0.6))
                                )
                        }
                    } else {
                        Rectangle()
                            .fill(Color.purple.opacity(0.3))
                    }
                }
                
                // Play overlay in center
                Image(systemName: "play.circle.fill")
                    .font(.system(size: 44))
                    .foregroundColor(.white)
                    .shadow(color: .black.opacity(0.3), radius: 4, x: 0, y: 2)

                // Title at bottom with better readability
                VStack {
                    Spacer()
                    Text(video.title)
                        .font(.system(.caption, design: .rounded, weight: .medium))
                        .lineLimit(3)
                        .multilineTextAlignment(.leading)
                        .padding(.horizontal, 8)
                        .padding(.vertical, 6)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(
                            LinearGradient(
                                colors: [Color.clear, Color.black.opacity(0.8)],
                                startPoint: .top,
                                endPoint: .bottom
                            )
                        )
                        .foregroundColor(.white)
                }
            }
            .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
        }
        .buttonStyle(PlainButtonStyle())
    }
}

// Optimized for medium widget - side by side layout
struct MediumVideoItem: View {
    let video: YoutubeVideoWidget

    var body: some View {
        Link(destination: URL(string: video.url)!) {
            VStack(spacing: 4) {
                // Thumbnail with 16:9 aspect ratio
                ZStack {
                    if let containerURL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: "group.com.ifochka.jufk.widgets") {
                        let imagePath = containerURL.appendingPathComponent("\(video.videoId).jpg")
                        
                        if let uiImage = UIImage(contentsOfFile: imagePath.path) {
                            Image(uiImage: uiImage)
                                .resizable()
                                .aspectRatio(16/9, contentMode: .fit)
                                .clipShape(RoundedRectangle(cornerRadius: 6, style: .continuous))
                        } else {
                            RoundedRectangle(cornerRadius: 6, style: .continuous)
                                .fill(LinearGradient(
                                    colors: [Color.purple.opacity(0.6), Color.blue.opacity(0.4)],
                                    startPoint: .topLeading,
                                    endPoint: .bottomTrailing
                                ))
                                .aspectRatio(16/9, contentMode: .fit)
                                .overlay(
                                    Image(systemName: "photo")
                                        .font(.title2)
                                        .foregroundColor(.white.opacity(0.6))
                                )
                        }
                    }
                    
                    // Play button overlay
                    Image(systemName: "play.circle.fill")
                        .font(.title)
                        .foregroundColor(.white)
                        .shadow(color: .black.opacity(0.3), radius: 2, x: 0, y: 1)
                }
                
                // Title with more space
                Text(video.title)
                    .font(.system(.caption2, design: .default, weight: .medium))
                    .lineLimit(3)
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundColor(.primary)
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
}

// Optimized for large widget - more spacious layout
struct LargeVideoItem: View {
    let video: YoutubeVideoWidget

    var body: some View {
        Link(destination: URL(string: video.url)!) {
            HStack(spacing: 12) {
                // Thumbnail
                ZStack {
                    if let containerURL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: "group.com.ifochka.jufk.widgets") {
                        let imagePath = containerURL.appendingPathComponent("\(video.videoId).jpg")
                        
                        if let uiImage = UIImage(contentsOfFile: imagePath.path) {
                            Image(uiImage: uiImage)
                                .resizable()
                                .aspectRatio(16/9, contentMode: .fit)
                                .frame(width: 120, height: 68)
                                .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
                        } else {
                            RoundedRectangle(cornerRadius: 8, style: .continuous)
                                .fill(LinearGradient(
                                    colors: [Color.purple.opacity(0.6), Color.blue.opacity(0.4)],
                                    startPoint: .topLeading,
                                    endPoint: .bottomTrailing
                                ))
                                .frame(width: 120, height: 68)
                                .overlay(
                                    Image(systemName: "photo")
                                        .font(.title3)
                                        .foregroundColor(.white.opacity(0.6))
                                )
                        }
                    }
                    
                    // Play button
                    Image(systemName: "play.circle.fill")
                        .font(.title2)
                        .foregroundColor(.white)
                        .shadow(color: .black.opacity(0.3), radius: 2, x: 0, y: 1)
                }
                
                // Title and description area
                VStack(alignment: .leading, spacing: 4) {
                    Text(video.title)
                        .font(.system(.caption, design: .default, weight: .medium))
                        .lineLimit(4)
                        .multilineTextAlignment(.leading)
                        .foregroundColor(.primary)
                    
                    Spacer()
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.horizontal, 4)
            .padding(.vertical, 6)
            .background(
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                    .fill(Color(UIColor.secondarySystemGroupedBackground))
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}

// Helper function for phase description
func phaseDescription(_ phase: AsyncImagePhase) -> String {
    switch phase {
    case .empty: return "empty (loading)"
    case .success: return "success"
    case .failure: return "failure"
    @unknown default: return "unknown"
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
