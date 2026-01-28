# Gradle Migration Documentation

## Overview

This project has been migrated from Apache Maven to Gradle, utilizing the current stable release of Gradle (9.3). The migration maintains all functionality while adding enhanced capabilities for standalone application packaging.

## Version Information

- **Gradle Version**: 9.3 (current stable release as of January 2026)
- **Java Version**: 21 (using Java Toolchain)
- **Build Tool**: Gradle with Wrapper (platform-independent)

## Migration Details

### Build Files Created

1. **build.gradle.kts** - Main build configuration file (replaces pom.xml)
2. **settings.gradle.kts** - Project settings and name configuration
3. **gradlew / gradlew.bat** - Gradle wrapper scripts for Unix/Windows
4. **gradle/wrapper/** - Gradle wrapper JAR and properties

### Dependencies Migrated

All Maven dependencies have been successfully migrated to Gradle format:

| Dependency | Version | Scope | Notes |
|------------|---------|-------|-------|
| commons-collections4 | 4.5.0 | implementation | Apache Commons Collections utilities |
| junit-jupiter | 5.14.0 | test | JUnit 5 API and engine for testing |
| junit-platform-launcher | 1.11.4 | testRuntime | Required for Gradle 9.x JUnit integration |

### Plugins Configured

1. **java** - Core Java compilation and build support
2. **application** - Provides `run` task and application packaging
3. **jacoco** - Code coverage reporting (version 0.8.14)
4. **spotless** - Code formatting with Eclipse formatter (version 6.25.0)
5. **shadow** - Creates standalone executable fat JARs (version 9.3.1)

### Build Configuration

#### Java Toolchain
The project uses Gradle's Java Toolchain feature to ensure Java 21 is used consistently:

```kotlin
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
```

#### Application Configuration
Main class is configured for execution:

```kotlin
application {
    mainClass.set("edu.luc.cs.consoleapp.Main")
}
```

#### JaCoCo Coverage
Code coverage is configured to:
- Run automatically after tests
- Exclude `Main*.class` files from coverage metrics (pedagogical reasons)
- Generate both XML and HTML reports
- Enforce coverage thresholds:
  - Instruction coverage: 80%
  - Method coverage: 100%
  - Line coverage: 90%
  - Class coverage: 100%
  - Complexity coverage: 100%

#### Spotless Code Formatting
Spotless is configured to:
- Apply Eclipse formatter with custom config (`.eclipse-formatter.xml`)
- Remove unused imports
- Trim trailing whitespace
- End files with newline
- Run automatically before compilation

## Standalone Execution

### Shadow Plugin Integration

The project now uses the Shadow plugin to create standalone executable JARs. This plugin:
- Bundles all dependencies into a single JAR file
- Configures the manifest with the correct main class
- Creates a "fat JAR" that can run anywhere with Java installed

### Generated Artifact

Running `./gradlew shadowJar` produces:
```
build/libs/consoleapp-0.3-jar-with-dependencies.jar
```

This JAR can be executed standalone:
```bash
java -jar build/libs/consoleapp-0.3-jar-with-dependencies.jar [args...]
```

**Example:**
```bash
$ echo -e "hello world\ntest" | java -jar build/libs/consoleapp-0.3-jar-with-dependencies.jar 3
[hello]
[hello, world]
[hello, world, test]
```

## Command Reference

### Maven vs Gradle Command Mapping

| Maven Command | Gradle Equivalent | Description |
|---------------|-------------------|-------------|
| `mvn clean` | `./gradlew clean` | Clean build artifacts |
| `mvn compile` | `./gradlew compileJava` | Compile source code |
| `mvn test` | `./gradlew test` | Run tests |
| `mvn package` | `./gradlew build` or `./gradlew shadowJar` | Build project |
| `mvn verify` | `./gradlew check` | Run verification tasks |
| `mvn exec:java` | `./gradlew run` | Execute main class |
| `mvn jacoco:report` | `./gradlew jacocoTestReport` | Generate coverage report |

### Common Gradle Commands

#### Build Commands
```bash
# Clean and build the project
./gradlew clean build

# Build without tests
./gradlew build -x test

# Create standalone JAR
./gradlew shadowJar

# Run all checks including coverage verification
./gradlew check
```

#### Execution Commands
```bash
# Run the application
./gradlew run

# Run with arguments (sliding queue capacity)
./gradlew run --args="3"

# Run a different main class
./gradlew run -PmainClass=edu.luc.cs.consoleapp.MainLeaky --args="3"
```

#### Testing Commands
```bash
# Run all tests
./gradlew test

# Run tests with coverage report
./gradlew test jacocoTestReport

# Run specific test class
./gradlew test --tests TestSlidingQueue

# Run tests continuously (on file changes)
./gradlew test --continuous
```

#### Code Quality Commands
```bash
# Apply code formatting
./gradlew spotlessApply

# Check code formatting
./gradlew spotlessCheck

# Generate coverage report
./gradlew jacocoTestReport

# Verify coverage thresholds
./gradlew jacocoTestCoverageVerification
```

#### Dependency Commands
```bash
# View dependency tree
./gradlew dependencies

# View test dependencies only
./gradlew dependencies --configuration testRuntimeClasspath

# Check for dependency updates
./gradlew dependencyUpdates
```

#### Information Commands
```bash
# List all tasks
./gradlew tasks

# List all tasks including detailed descriptions
./gradlew tasks --all

# View project properties
./gradlew properties
```

## Project Structure

The source directory structure remains unchanged from Maven:

```
consoleapp-java/
├── build.gradle.kts          # Gradle build configuration
├── settings.gradle.kts        # Gradle project settings
├── gradlew                    # Unix Gradle wrapper script
├── gradlew.bat               # Windows Gradle wrapper script
├── .eclipse-formatter.xml    # Eclipse formatter config for Spotless
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       └── edu/
│   │           └── luc/
│   │               └── cs/
│   │                   └── consoleapp/
│   │                       ├── Main.java
│   │                       ├── MainLeaky.java
│   │                       ├── MainStream.java
│   │                       ├── MainTestable.java
│   │                       ├── OutputHandler.java
│   │                       └── SlidingQueue.java
│   └── test/
│       └── java/
│           └── edu/
│               └── luc/
│                   └── cs/
│                       └── consoleapp/
│                           ├── OutputToList.java
│                           ├── TestMainLeaky.java
│                           ├── TestMainNotDRY.java
│                           ├── TestSlidingQueue.java
│                           └── TestSlidingQueueInteractive.java
└── build/                    # Gradle build output (gitignored)
    ├── classes/
    ├── libs/                 # Contains generated JARs
    ├── reports/              # Test and coverage reports
    └── test-results/
```

## Build Output Locations

| Artifact Type | Maven Location | Gradle Location |
|---------------|----------------|-----------------|
| Compiled classes | `target/classes/` | `build/classes/java/main/` |
| Test classes | `target/test-classes/` | `build/classes/java/test/` |
| JAR files | `target/*.jar` | `build/libs/*.jar` |
| Test reports | `target/surefire-reports/` | `build/reports/tests/test/` |
| Coverage reports | `target/site/jacoco/` | `build/reports/jacoco/test/html/` |

## Viewing Reports

### Test Reports

After running tests, view the HTML test report:

On macOS:
```bash
open build/reports/tests/test/index.html
```

On Linux:
```bash
xdg-open build/reports/tests/test/index.html
```

### Coverage Reports

After running `./gradlew jacocoTestReport`, view the HTML coverage report:

On macOS:
```bash
open build/reports/jacoco/test/html/index.html
```

On Linux:
```bash
xdg-open build/reports/jacoco/test/html/index.html
```

## Advanced Features

### Configuration Cache

Gradle 9.x supports configuration cache for faster builds. Enable it by adding to `gradle.properties`:

```properties
org.gradle.configuration-cache=true
```

### Build Scans

Generate detailed build analysis:
```bash
./gradlew build --scan
```

### Parallel Execution

Enable parallel test execution by adding to `build.gradle.kts`:

```kotlin
tasks.test {
    maxParallelForks = Runtime.getRuntime().availableProcessors().div(2).takeIf { it > 0 } ?: 1
}
```

## GitHub Actions CI/CD

The GitHub Actions workflow has been updated to use Gradle:

**File**: `.github/workflows/java-gradle.yml`

Key changes:
- Renamed from `java-maven.yml` to `java-gradle.yml`
- Uses `gradle/actions/setup-gradle@v4` action
- Runs `./gradlew test` instead of `mvn verify`
- Builds standalone JAR with `./gradlew shadowJar`
- Still uploads coverage to Codecov

## Migration Benefits

1. **Faster Builds**: Gradle's incremental compilation and build cache
2. **Better Dependency Management**: Transitive dependency resolution
3. **Flexible Scripting**: Kotlin DSL for type-safe build logic
4. **Rich Plugin Ecosystem**: Easy integration of new build tools
5. **Standalone Packaging**: Built-in support for fat JAR creation via Shadow plugin
6. **Modern Tooling**: Active development and Java 21+ support
7. **Improved IDE Integration**: Better support in IntelliJ IDEA, Eclipse, and VS Code

## Troubleshooting

### Gradle Daemon Issues
```bash
# Stop all Gradle daemons
./gradlew --stop

# Check daemon status
./gradlew --status
```

### Clean Build
```bash
# Force clean build
./gradlew clean build --no-build-cache
```

### Wrapper Update
```bash
# Update to latest Gradle version
./gradlew wrapper --gradle-version=9.3
```

### Dependency Conflicts
```bash
# View dependency insight
./gradlew dependencyInsight --dependency junit-jupiter
```

### Permission Issues (Unix/Linux/macOS)
```bash
# Make gradlew executable
chmod +x gradlew
```

## Compatibility Notes

- **Java 21**: Required by project configuration
- **Gradle 9.3**: Current stable release
- **Shadow Plugin 9.3.1**: Latest version for fat JAR creation
- **Spotless 6.25.0**: Compatible with Gradle 9.x
- **JUnit 5**: Platform launcher explicitly included for Gradle 9.x
- **IDE Support**: Works with IntelliJ IDEA, Eclipse, VS Code with Gradle extensions

## Key Differences from Maven

### Dependency Scopes
- Maven `compile` → Gradle `implementation`
- Maven `test` → Gradle `testImplementation`
- Maven `runtime` → Gradle `runtimeOnly`
- Maven `testRuntime` → Gradle `testRuntimeOnly`

### Build Lifecycle
- Gradle uses task-based execution rather than Maven's phase-based lifecycle
- Tasks can have dependencies and run conditionally
- Gradle caches task outputs for faster incremental builds

### Configuration
- Gradle uses `build.gradle.kts` (Kotlin DSL) instead of `pom.xml` (XML)
- More concise and type-safe configuration
- Programmatic build logic using Kotlin

## References

- [Gradle Documentation](https://docs.gradle.org/9.3/userguide/userguide.html)
- [Gradle Shadow Plugin](https://github.com/GradleUp/shadow)
- [Gradle Java Plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
- [Gradle Application Plugin](https://docs.gradle.org/current/userguide/application_plugin.html)
- [Gradle JaCoCo Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- [Spotless Gradle Plugin](https://github.com/diffplug/spotless)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Migrating from Maven to Gradle](https://docs.gradle.org/current/userguide/migrating_from_maven.html)
