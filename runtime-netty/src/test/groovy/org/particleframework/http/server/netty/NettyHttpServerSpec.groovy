/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.particleframework.http.server.netty

import org.particleframework.context.ApplicationContext
import org.particleframework.core.io.socket.SocketUtils
import org.particleframework.http.binding.annotation.Parameter
import org.particleframework.runtime.ParticleApplication
import org.particleframework.stereotype.Controller
import org.particleframework.web.router.annotation.Get
import org.particleframework.web.router.annotation.Put
import spock.lang.Specification

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class NettyHttpServerSpec extends Specification {


    void "test Particle server running"() {
        when:
        ApplicationContext applicationContext = ParticleApplication.run()

        then:
        new URL("http://localhost:8080/person/Fred").getText(readTimeout:3000) == "Person Named Fred"

        cleanup:
        applicationContext?.stop()
    }

    void "test Particle server running again"() {
        when:
        ApplicationContext applicationContext = ParticleApplication.run()

        then:
        new URL("http://localhost:8080/person/Fred").getText(readTimeout:3000) == "Person Named Fred"

        cleanup:
        applicationContext?.stop()
    }

    void "test Particle server on different port"() {
        when:
        int newPort = SocketUtils.findAvailableTcpPort()
        ApplicationContext applicationContext = ParticleApplication.run('-port',newPort.toString())

        then:
        new URL("http://localhost:$newPort/person/Fred").getText(readTimeout:3000) == "Person Named Fred"

        cleanup:
        applicationContext?.stop()
    }

    void "test bind method argument from request parameter"() {
        when:
        int newPort = SocketUtils.findAvailableTcpPort()
        ApplicationContext applicationContext = ParticleApplication.run('-port',newPort.toString())

        then:
        new URL("http://localhost:$newPort/person/another/job?id=10").getText(readTimeout:3000) == "JOB ID 10"

        cleanup:
        applicationContext?.stop()
    }

    void "test bind method argument from request parameter when parameter missing"() {
        when:"A required request parameter is missing"
        int newPort = SocketUtils.findAvailableTcpPort()
        ApplicationContext applicationContext = ParticleApplication.run('-port',newPort.toString())
        new URL("http://localhost:$newPort/person/another/job").getText(readTimeout:3000)

        then:"A 404 is returned"
        thrown(FileNotFoundException)

        cleanup:
        applicationContext?.stop()
    }


    @Controller
    static class PersonController {

        @Get('/{name}')
        String name(String name) {
            "Person Named $name"
        }

        @Put('/job/{name}')
        void doWork(String name) {
            println 'doing work'
        }

        @Get('/another/job')
        String doMoreWork(int id) {
            "JOB ID $id"
        }
    }
}