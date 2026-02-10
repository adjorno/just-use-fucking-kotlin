# typed: false
# frozen_string_literal: true

# This formula is auto-updated by GitHub Actions on each release
class Jufk < Formula
  desc "Just Use Fucking Kotlin - One language. One codebase. Every platform."
  homepage "https://justusefuckingkotlin.com"
  version "0.1.0"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v0.1.0/jufk-macos-arm64"
      sha256 "PLACEHOLDER_SHA256_MACOS_ARM64"
    end
    if Hardware::CPU.intel?
      url "https://github.com/adjorno/JUFK/releases/download/v0.1.0/jufk-macos-x64"
      sha256 "PLACEHOLDER_SHA256_MACOS_X64"
    end
  end

  on_linux do
    if Hardware::CPU.intel?
      url "https://github.com/adjorno/JUFK/releases/download/v0.1.0/jufk-linux-x64"
      sha256 "PLACEHOLDER_SHA256_LINUX_X64"
    end
  end

  def install
    if OS.mac?
      if Hardware::CPU.arm?
        bin.install "jufk-macos-arm64" => "jufk"
      else
        bin.install "jufk-macos-x64" => "jufk"
      end
    elsif OS.linux?
      if Hardware::CPU.intel?
        bin.install "jufk-linux-x64" => "jufk"
      end
    end
  end

  test do
    system "#{bin}/jufk"
  end
end
