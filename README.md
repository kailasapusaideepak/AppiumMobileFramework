# AppiumMobileFramework
### Senior SDET Mobile Testing Project — Appium 2.x + Java + TestNG

---

## Prerequisites (install in this order)

| Tool | Version | Command to verify |
|------|---------|-------------------|
| Java JDK | 11+ | `java -version` |
| Maven | 3.8+ | `mvn -version` |
| Node.js | 18+ | `node -v` |
| Appium | 2.x | `appium -v` |
| UiAutomator2 Driver | latest | `appium driver list --installed` |
| Android Studio | any | for platform-tools / adb |
| Appium Inspector | latest | GUI tool for locators |

---

## Step-by-Step Setup

### 1. Install Appium 2 and the Android driver
```bash
npm install -g appium
appium driver install uiautomator2
```

### 2. Set environment variables (add to ~/.bash_profile or system env)
```bash
export ANDROID_HOME=/Users/<you>/Library/Android/sdk        # Mac
# Windows: C:\Users\<you>\AppData\Local\Android\sdk

export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools
```

### 3. Connect your Android device
- Enable Developer Options: Settings → About Phone → tap Build Number 7 times
- Enable USB Debugging: Settings → Developer Options → USB Debugging ON
- Run: `adb devices` — your device ID should appear

### 4. Download ApiDemos APK
```
URL: https://github.com/appium/appium/raw/master/packages/appium/sample-code/apps/ApiDemos-debug.apk
Save to: AppiumMobileFramework/apps/ApiDemos-debug.apk
```

### 5. Update config.properties
```
src/test/resources/config.properties
```
Set `device.name` to exactly what `adb devices` shows you.

### 6. Open in IntelliJ
- File → Open → select the `AppiumMobileFramework` folder
- IntelliJ will auto-detect Maven and download dependencies (takes 2–3 min)
- Wait for the indexing bar at bottom to complete

### 7. Start Appium server (in a separate terminal, keep it running)
```bash
appium --relaxed-security
```
You should see: `Appium REST http interface listener started on 0.0.0.0:4723`

### 8. Run tests
**Option A — IntelliJ UI:**
Right-click `HomePageTest.java` → Run

**Option B — Maven:**
```bash
mvn test
```

**Option C — TestNG XML (runs all suites):**
Right-click `testng.xml` → Run

---

## Project Structure
```
AppiumMobileFramework/
├── apps/                          ← Place your APK here
├── screenshots/                   ← Auto-generated on test failure
├── reports/
│   └── ExtentReport.html          ← Open in browser after test run
├── src/
│   ├── main/java/com/sdet/mobile/
│   │   ├── pages/
│   │   │   ├── HomePage.java      ← Page Object for home screen
│   │   │   └── ViewsPage.java     ← Page Object for Views section
│   │   └── utils/
│   │       ├── ConfigReader.java       ← Reads config.properties
│   │       ├── ExtentReportManager.java← HTML report setup
│   │       └── ScreenshotUtil.java     ← Screenshot capture
│   └── test/java/com/sdet/mobile/tests/
│       ├── BaseTest.java          ← Driver init, teardown, report hooks
│       ├── HomePageTest.java      ← 4 tests on home screen
│       └── ScrollSwipeTest.java   ← W3C Actions scroll/swipe tests
├── src/test/resources/
│   ├── config.properties          ← Device & app config (YOU EDIT THIS)
│   └── testng.xml                 ← Test suite definition
└── pom.xml                        ← Maven dependencies
```

---

## What interviewers will ask about this project

**Q: Why UiAutomator2Options instead of DesiredCapabilities?**
A: DesiredCapabilities was deprecated in Appium 2.x. UiAutomator2Options is type-safe and driver-specific.

**Q: Why WebDriverWait instead of Thread.sleep?**
A: Thread.sleep is a fixed wait regardless of element state. WebDriverWait polls until the condition is true or timeout is reached — faster and more reliable.

**Q: Why W3C Actions instead of TouchAction?**
A: TouchAction and MultiTouchAction were deprecated in Appium 2.x. W3C Actions is the standard cross-platform gesture API.

**Q: How would you run these in parallel?**
A: Add `parallel="tests" thread-count="2"` to the suite tag in testng.xml, and make the driver ThreadLocal in BaseTest.

---

## Troubleshooting

| Error | Fix |
|-------|-----|
| `adb devices` shows nothing | Re-enable USB Debugging, try different cable |
| `Cannot find app` | Check absolute path in config.properties, APK must be in /apps |
| `Connection refused 4723` | Appium server not running — run `appium --relaxed-security` |
| `No such driver: uiautomator2` | Run `appium driver install uiautomator2` |
| Maven dependency errors | File → Invalidate Caches → Restart in IntelliJ |
