package rk.prov.performancetest;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log
public class DbService
{
    JdbcTemplate jdbcTemplate;

    private final String tps =
            "select\n" +
            "count(*) counter\n" +
            "from\n" +
            "om_hist$order_header\n" +
            "where\n" +
            "hist_order_state_id = 4 and\n" +
            "task_type in ('A','M','C') and\n" +
            "timestamp_in between '2019-10-04' and '2019-10-05'";

    public String getTps()
    {
        return jdbcTemplate.query(
                tps,
        (rs, rowNum) -> rs.getString("counter")).get(0);
    }
}
