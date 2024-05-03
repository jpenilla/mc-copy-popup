// import me.modmuss50.mpp.ReleaseType
import xyz.jpenilla.resourcefactory.fabric.Environment

plugins {
  val indraVersion = "3.1.3"
  id("net.kyori.indra") version indraVersion
  id("net.kyori.indra.git") version indraVersion
  id("net.kyori.indra.licenser.spotless") version indraVersion
  id("quiet-fabric-loom") version "1.6-SNAPSHOT"
  // id("me.modmuss50.mod-publish-plugin") version "0.5.1"
  id("xyz.jpenilla.resource-factory-fabric-convention") version "1.1.1"
}

decorateVersion()

repositories {
  mavenCentral()
  maven("https://maven.fabricmc.net/")
}

dependencies {
  minecraft(libs.minecraft)
  mappings(loom.officialMojangMappings())
  modImplementation(libs.fabricLoader)
  modImplementation(libs.fabricApi)
}

fabricModJson {
  name = "Copy Popup"
  author("jmp")
  contact {
    val githubUrl = "https://github.com/jpenilla/mc-copy-popup"
    homepage = githubUrl
    sources = githubUrl
    issues = "$githubUrl/issues"
  }
  environment = Environment.CLIENT
  apache2License()
  depends("fabric", "*")
  depends("fabricloader", ">=${libs.versions.fabricLoader.get()}")
  depends("minecraft", "~1.20.6")
  mixin("mc-copy-popup.mixins.json")
}

tasks {
  jar {
    from("LICENSE") {
      rename { "LICENSE_${project.name}" }
    }
  }
  remapJar {
    archiveFileName.set("${project.name}-mc${libs.versions.minecraft.get()}-${project.version}.jar")
  }
  withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:-processing")
  }
}

indra {
  javaVersions {
    target(21)
  }
  github("jpenilla", "mc-copy-popup")
  apache2License()
}

indraSpotlessLicenser {
  licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
}

/*
publishMods.modrinth {
    projectId = "PExmWQV8"
    type = ReleaseType.STABLE
    file = tasks.remapJar.flatMap { it.archiveFile }
    changelog = providers.environmentVariable("RELEASE_NOTES")
    accessToken = providers.environmentVariable("MODRINTH_TOKEN")
    modLoaders.add("fabric")
    minecraftVersions.add(libs.versions.minecraft)
}
 */

fun decorateVersion() {
  val versionString = version as String
  val decorated = if (versionString.endsWith("-SNAPSHOT")) {
    "$versionString+${indraGit.commit()?.name?.substring(0, 7) ?: error("Could not determine git hash")}"
  } else {
    versionString
  }
  version = decorated
}
