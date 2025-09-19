name := "consoleapp"

version := "0.2"

libraryDependencies ++= Seq(
  "org.apache.commons" %  "commons-collections4" % "4.5.0",
  "com.github.sbt.junit" % "jupiter-interface" % "0.15.1" % Test,
  "net.jqwik"   % "jqwik"             % "1.9.3"  % Test
)

enablePlugins(JavaAppPackaging)
