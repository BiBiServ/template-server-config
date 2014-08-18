/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.cebitec.bibiserv.statistic;

import de.unibi.cebitec.bibiserv.statistic.data.Clicks;
import de.unibi.cebitec.bibiserv.statistic.data.ClientInfo;
import de.unibi.cebitec.bibiserv.statistic.data.Download;
import de.unibi.cebitec.bibiserv.statistic.data.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jkrueger
 */
public class StatisticTestData {

    private Connection con = null;
    private boolean verbose = true;

    private int n = 10;
    private Timestamp from = new Timestamp(114, 0, 1, 0, 0, 0, 0);
    private Timestamp to = new Timestamp(System.currentTimeMillis());

    public enum USER {

        juser, jkrueger, jschmolke, asczyrba, mrumming, robert, anonymous
    };

    public enum TOOLID {

        dca, dialign, jali, predictor, reputer, genefisher2, acms, guugle, knotinframe, palikiss, pkiss, pknotsrg, rnaalishapes, rnahybrid, rnashapes
    }

    public enum Browser {

        Firefox, Safari, Chrome, InternetExplorer
    }

    public enum Device {

        unknown, Workstation, Smartphone, Tablet
    }

    public enum OS {

        Solaris, Linux, OSX, IOS, WindowsPhone, Windows
    }

    public enum COUNTRY {

        AF, AL, DZ, AS, AD, AO, AI, AQ, AG, AR, AM, AW, AU, AT, AZ, BS, BH, BD, BB, BY, BE, BZ, BJ, BM, BT, BO, BA, BW, BV, BR, IO, BN, BG, BF, BI, KH, CM, CA, CV, KY, CF, TD, CL, CN, CX, CC, CO, KM, CG, CD, CK, CR, CI, HR, CU, CY, CZ, DK, DJ, DM, DO, TP, EC, EG, SV, GQ, ER, EE, ET, FK, FO, FJ, FI, FR, FX, GF, PF, TF, GA, GM, GE, DE, GH, GI, GR, GL, GD, GP, GU, GT, GN, GW, GY, HT, HM, VA, HN, HK, HU, IS, IN, ID, IR, IQ, IE, IL, IT, JM, JP, JO, KZ, KE, KI, KP, KR, KW, KG, LA, LV, LB, LS, LR, LY, LI, LT, LU, MO, MK, MG, MW, MY, MV, ML, MT, MH, MQ, MR, MU, YT, MX, FM, MD, MC, MN, ME, MS, MA, MZ, MM, NA, NR, NP, NL, AN, NC, NZ, NI, NE, NG, NU, NF, MP, NO, OM, PK, PW, PA, PG, PY, PE, PH, PN, PL, PT, PR, QA, RE, RO, RU, RW, KN, LC, VC, WS, SM, ST, SA, SN, RS, SC, SL, SG, SK, SI, SB, SO, ZA, SS, GS, ES, LK, SH, PM, SD, SR, SJ, SZ, SE, CH, SY, TW, TJ, TZ, TH, TG, TK, TO, TT, TN, TR, TM, TC, TV, UG, UA, AE, GB, US, UM, UY, UZ, VU, VE, VN, VG, VI, WF, EH, YE, ZM, ZW
    }

    public static long random(long max) {
        return random(0, max);
    }

    public static int random(int max) {
        return random(0, max);
    }

    public static int random(int min, int max) {
        return (int) random((long) min, (long) max);
    }

    public static long random(long min, long max) {
        return (long) ((Math.random() * (max - min + 1)) + min);
    }

    public static Timestamp createTimestamp(Timestamp from, Timestamp to) {
        return new Timestamp(random(from.getTime(), to.getTime()));
    }

    public static Timestamp increase(Timestamp time) {
        return new Timestamp(time.getTime() + random(60000));
    }

    public String createStats() throws SQLException {

        StringBuilder sb = new StringBuilder();

        //20 % REST
        if (Math.random() <= 0.2) {
            Status status = new Status(TOOLID.values()[random(TOOLID.values().length - 1)].name(), "fct" + random(1, 3), USER.anonymous.name(), createTimestamp(from, to));
            status.store(con);
            sb.append("R");

        } else {
            //80 % WEB
            Timestamp timestamp = createTimestamp(from, to);
            String user = USER.values()[random(USER.values().length - 1)].name();

            // ClientInfo
            ClientInfo ci = ClientInfo.create(timestamp);
            ci.store(con);
            sb.append("I");

            double stay = 1;

            while (stay > 0.5) {

                // choose tool
                String id = TOOLID.values()[random(TOOLID.values().length - 1)].name();
                // click tool
                Clicks click = new Clicks(ci.getSessionid(), id, user, timestamp);
                click.store(con);
                sb.append("C");

                // 30 % usage
                if (Math.random() <= 0.3) {
                    timestamp = increase(timestamp);

                    Status status = new Status(ci.getSessionid(), id, "fct" + random(1, 3), user, timestamp);
                    status.store(con);
                    sb.append("W");
                }
                // 1 % Download
                if (Math.random() <= 0.01) {
                    timestamp = increase(timestamp);
                    Download download = new Download(ci.getSessionid(), id, id + ".zip", timestamp);
                    download.store(con);
                    sb.append("D");
                }

                stay = stay * Math.random();
                timestamp = increase(timestamp);
            }

        }
        return sb.toString();
    }

    public static Timestamp String2Timestamp(String timestamp) throws Exception {
        try {
            Pattern p = Pattern.compile("(\\d{1,2})\\/(\\d{1,2})\\/(\\d{1,2})");
            Matcher m = p.matcher(timestamp);
            if (m.matches()) {
                int year = Integer.parseInt(m.group(3));
                int month = Integer.parseInt(m.group(2));
                int day = Integer.parseInt(m.group(1));
                return new Timestamp(year + 100, month - 1, day, 0, 0, 0, 0);
            }
        } catch (NumberFormatException e) {
        }
        throw new Exception("Invalid timestamp pattern. Must be \\d{1,2}/\\d{1,2}/\\d{1,2}!");

    }

    public void checkProperties() throws Exception {
        // look for properties

        if (System.getProperty("verbose") != null) {
            verbose = Boolean.getBoolean(System.getProperty("verbose"));
        }

        // number of repetations (default is 10)
        if (System.getProperty("n") != null) {
            try {
                n = Integer.parseInt(System.getProperty("n"));
            } catch (NumberFormatException e) {
                throw new Exception("Invalid value for 'n', must be a positive integer value.");
            }
        }

        // check from / to Properties
        if (System.getProperty("from") != null) {
            from = String2Timestamp(System.getProperty("from"));
        }

        if (System.getProperty("to") != null) {
            to = String2Timestamp(System.getProperty("to"));
        }

        if (from.getTime() > to.getTime()) {
            throw new Exception("'From' date must be before 'To' date!");
        }

        // test
        if (System.getProperty("test") != null) {
            System.out.println("Repeat '" + n + "' times.");
            System.out.println("from is " + from.toString());
            System.out.println("to is " + to.toString());
            System.out.println("Verbose is " + verbose);
            throw new Exception("running test mode");
        }

    }

    public void help() {
        System.out.println("Supports following System properties :");
        System.out.println("-Dverbose=[true]|false   : enable/disable verbose output");
        System.out.println("-Dn=INTEGER              : number of simulated access entries [" + n + "]");
        System.out.println("-Ddbhost=                : database host (mandatory)");
        System.out.println("-Ddbport=                : database port (mandatory)");
        System.out.println("-Ddbuser=                : database user (mandatory)");
        System.out.println("-Ddbpwd=                 : database password (mandatory)");
        System.out.println("-Dfrom=DD/MM/YY          : start [" + from.getDate() + "/" + (from.getMonth() + 1) + "/" + (from.getYear() - 100) + "]");
        System.out.println("-Dto=DD/MM/YY            : to [" + to.getDate() + "/" + (to.getMonth() + 1) + "/" + (to.getYear() - 100) + "]");
    }

    public void run() throws SQLException {
        for (int i = 0; i < n; i++) {
            if (verbose) {
                System.out.println(createStats());
            } else {
                createStats();
            }
        }
    }

    public void setConnection(Connection connection) {
        con = connection;
    }

    public static void main(String[] args) {

        StatisticTestData std = new StatisticTestData();

        try {

            if (System.getProperty("dbhost") == null || System.getProperty("dbport") == null || System.getProperty("dbuser") == null || System.getProperty("dbpwd") == null) {
                throw new Exception("Invalid or missing argument!");

            }

            // dbhost
            String host = System.getProperty("dbhost");
            // dbport
            int port;
            try {
                port = Integer.parseInt(System.getProperty("dbport"));
                if (port <= 0 || port >= 65535) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new Exception("Invalid port");
            }
            // db user && password
            String user = System.getProperty("dbuser");
            String pwd = System.getProperty("dbpwd");

            String jdbcurl = "jdbc:derby://" + host + ":" + port + "/bibiserv2;user=" + user + ";password=" + pwd;

            std.checkProperties();

            // prepare jdbc connection
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                std.setConnection(DriverManager.getConnection(jdbcurl));
                std.run();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            std.help();
        }

    }

}
