plugins {
    idea
    java
    id("gg.essential.loom") version "1.10.+"
    id("com.gradleup.shadow") version "9.2.+"
}

val modID: String by project
val modName: String by project
val mixinFileName: String = "barrierVisibility"
val modGroup: String by project
val modVersion: String by project
val mcVersion = "1.8.9"

group = modGroup
version = modVersion

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    runConfigs {
        getByName("client") {
            property("mixin.debug.verbose", "true")
            property("mixin.debug.export", "true")
            programArgs("--tweakClass", "org.spongepowered.asm.launch.MixinTweaker", "--mixin", "mixins.$mixinFileName.json")
        }
        remove(getByName("server"))
    }

    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        mixinConfig("mixins.$mixinFileName.json")
    }

    mixin {
        defaultRefmapName.set("mixins.$mixinFileName.refmap.json")
    }

    // For some reason loom defaults to tab indentation
    decompilers {
        named("vineflower") {
            options.put("indent-string", "    ")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/repository/maven-public/")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

val shade: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    modRuntimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
    shade("org.spongepowered:mixin:0.7.11-SNAPSHOT") {
        isTransitive = false
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        inputs.property("modID", modID)
        inputs.property("modName", modName)
        inputs.property("version", version)
        inputs.property("mcVersion", mcVersion)

        filesMatching(listOf("mcmod.info", "mixins.$mixinFileName.json")) {
            expand(inputs.properties) {
                escapeBackslash = true
            }
        }
    }

    jar {
        manifest.attributes(mapOf(
            "FMLCorePluginContainsFMLMod" to true,
            "ForceLoadAsMod" to true,
            "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
            "MixinConfigs" to "mixins.$mixinFileName.json",
            "FMLCorePlugin" to "me.oondanomala.barriervisibility.mixins.MixinLoader",
        ))
    }

    shadowJar {
        archiveClassifier.set("dev")
        configurations = listOf(shade)
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        archiveClassifier.set("")
    }
}
