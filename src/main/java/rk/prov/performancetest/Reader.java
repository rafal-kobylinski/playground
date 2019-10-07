package rk.prov.performancetest;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rk.prov.performancetest.connectors.MqClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
@Log
public class Reader
{
    MqClient mq;

    public void sendRequests(String file)
    {
        AtomicLong timer = new AtomicLong(0);

        try {
            Files.lines(Paths.get(file))
                    .forEach(line -> {
                        String[] splitted = line.split(";");
                        LocalTime t = LocalTime.parse(splitted[1]);
                        if (timer.get() == 0) {
                            timer.set(t.toNanoOfDay());
                        }
                        Long diffMili = (t.toNanoOfDay() - timer.get())/1000000;
                        timer.set(t.toNanoOfDay());
                        log.info("Sending request: " + splitted[2]);
                        mq.put(splitted[2]);
                        log.info("req time: " + splitted[1] + ", waiting milisec: " + diffMili);
                        try {
                            Thread.sleep(diffMili);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
