/*
 * Kotlin
 *
 * Copyright 2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.remove

plugins {
    id("com.microej.gradle.application") version "0.16.0"
    id("com.google.protobuf") version "0.9.4"
}

group="com.microej.example"
version="2.0.0"

microej {
    applicationEntryPoint  = "com.microej.example.protobuf3example.Main"
    skippedCheckers = "nullanalysis"
}

dependencies {
    implementation("ej.api:edc:1.3.5")
    implementation("ej.library.eclasspath:logging:1.2.1")
    implementation("com.google:protobuf3:1.3.0")
    microejVee("com.microej.veeport.st.stm32f7508-dk:M5QNX_eval:2.2.0")
}

// Repositories configuration for artifacts "com.google.protobuf:protoc" and "com.google.protobuf:protoc-gen-javalite".
repositories {
    mavenCentral()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.27.0"
    }
    plugins {
        id("javalite") {
            artifact = "com.google.protobuf:protoc-gen-javalite:3.0.0"
        }
    }
    generateProtoTasks {
        all().configureEach {
            // generate only java-lite code (optimized for embedded devices).
            builtins{
                remove("java")
            }
            plugins {
                id("javalite")
            }
        }
    }
}