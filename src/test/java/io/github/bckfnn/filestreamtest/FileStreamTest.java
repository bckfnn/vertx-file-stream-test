package io.github.bckfnn.filestreamtest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class FileStreamTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();
    
    @Test
    public void testPauseAtEnd(TestContext context) throws Exception {
        Vertx vertx = rule.vertx();
        
        Async async = context.async();

        vertx.fileSystem().open("pom.xml", new OpenOptions(), openAr -> {
            if (openAr.succeeded()) {
                AsyncFile file = openAr.result();
                file.endHandler($ -> {
                   file.pause();
                   file.resume();
                   async.complete();
                });
                file.handler(b -> { });
            } else {
                context.fail(openAr.cause());
            }
        });
        
    }
}
