import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        ComposeApp.InitKoinIOSKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}