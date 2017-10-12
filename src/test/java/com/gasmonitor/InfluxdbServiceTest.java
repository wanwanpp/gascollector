package com.gasmonitor;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * InfluxdbService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 12, 2017</pre>
 */
public class InfluxdbServiceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: writeToInfluxdb(GasEvent e, String measurement)
     */
    @Test
    public void testWriteToInfluxdb() throws Exception {
        String s = "2017-10-12 14:32:19";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = dateFormat.parse(s);
        System.out.print(d);

//TODO: Test goes here... 
    }

    /**
     * Method: query(String tenant, String hardwareId, long begin, long end)
     */
    @Test
    public void testQuery() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: initPara(String url1, String dbName1, String user1, String password1, String retention1, int batchNum1)
     */
    @Test
    public void testInitPara() throws Exception {
//TODO: Test goes here... 
    }


} 
