scalaVersion := "3.7.3"

compile / javacOptions += "-Xlint:all"

javaOptions += "-enableassertions"

jacocoReportSettings := JacocoReportSettings()
  .withThresholds(
    JacocoThresholds(
      instruction = 80,
      method = 100,
      branch = 0, // FIXME workaround 0 of 0 missed = 0% bug
      complexity = 100,
      line = 90,
      clazz = 100)
  )
  .withFormats(JacocoReportFormats.HTML, JacocoReportFormats.XML)

jacocoExcludes := Seq("**.Main*")
