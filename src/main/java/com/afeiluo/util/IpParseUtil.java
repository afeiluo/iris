package com.afeiluo.util;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class IpParseUtil {
    private static Logger logger = LoggerFactory.getLogger(IpParseUtil.class);

    /**
     * ip区域判断
     */
    private static RangeSet<DistIpRange> rangeSet = TreeRangeSet.create();

    public static final String DEF_SPLITER = "\t";

    /**
     * 读取ip段信息并构建线段树
     */
    static {
        Scanner scanner = null;
        try {
            PathMatchingResourcePatternResolver prpr = new PathMatchingResourcePatternResolver();
            Resource resource = prpr.getResource("ip.quxian.txt");
            if (resource.exists()) {
                initDistIps(resource.getInputStream(), null);
                logger.info("Load ip.quxian.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static void initDistIps(InputStream inputStream, String spliter) {
        Scanner scanner = null;
        try {
            if (spliter == null) {
                spliter = DEF_SPLITER;
            }
            scanner = new Scanner(inputStream, "utf-8");
            int count = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] words = line.split(spliter);
                if (words == null || words.length <= 6) {
                    System.out.println("Dist Ip not legal! " + line);
                    continue;
                }
                long ip1 = ip2Long(words[0]);
                long ip2 = ip2Long(words[1]);
                String country = StringUtils.isBlank(words[2]) ? "未知" : words[2].trim();
                String provice = StringUtils.isBlank(words[3]) ? "未知" : words[3].trim();
                String city = StringUtils.isBlank(words[4]) ? "未知" : words[4].trim();
                String dist = StringUtils.isBlank(words[5]) ? "未知" : words[5].trim();
                double longitude = Double.parseDouble(words[8].trim());
                double latitude = Double.parseDouble(words[9].trim());
                DistIpRange pir1 = DistIpRange.getInstance(ip1, country, provice, city, dist, longitude, latitude);
                DistIpRange pir2 = DistIpRange.getInstance(ip2, country, provice, city, dist, longitude, latitude);
                Range<DistIpRange> range = Range.closed(pir1, pir2);
                rangeSet.add(range);
                count++;
            }
            logger.info("Total ip set is:" + count);
        } catch (Exception e) {
            logger.error("init Dist Ips error:{}", e.getMessage(), e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static Range<DistIpRange> findIpRange(String ip) {
        DistIpRange pir = DistIpRange.getInstance(ip2Long(ip), null, null, null, null, 0d, 0d);
        if (rangeSet.contains(pir)) {
            Range<DistIpRange> tmp = rangeSet.rangeContaining(pir);
            logger.debug("{}===>{}----{}", ip, tmp.lowerEndpoint(), tmp.upperEndpoint());
            return tmp;
        }
        return null;
    }

    /**
     * 转化ip字符串为long值（本来应该转化成无符号的32位整形，但是java不支持，所以用long）
     * 
     * @param ipStr ip字符
     * @return
     */
    public static long ip2Long(String ipStr) {
        String[] tmp = ipStr.split("[.]");
        long ip = 0L | Long.valueOf(tmp[0]) << 24;
        ip = ip | (Long.valueOf(tmp[1]) << 16);
        ip = ip | (Long.valueOf(tmp[2]) << 8);
        ip = ip | Long.valueOf(tmp[3]);
        if (ip < 0) {
            System.out.println("Ip2Long Error!!!! ip=" + ip);
        }
        return ip;
    }

    public static void main(String[] args) {
        String testIp = "58.254.172.48";
        Range<DistIpRange> range = findIpRange(testIp);
        logger.info("\nrange start:" + range.lowerEndpoint() + "\nrange end:" + range.upperEndpoint());
    }
}

class DistIpRange implements Comparable<DistIpRange> {
    private String country;
    private String provice;
    private String city;
    private String dist;
    private long value;
    private double longitude;
    private double latitude;

    public static DistIpRange getInstance(long value, String country, String provice, String city, String dist, double longitude, double latitude) {
        DistIpRange ret = new DistIpRange();
        ret.country = country;
        ret.provice = provice;
        ret.city = city;
        ret.dist = dist;
        ret.value = value;
        ret.longitude = longitude;
        ret.latitude = latitude;
        return ret;
    }

    @Override
    public int compareTo(DistIpRange distIpRange) {
        return value > distIpRange.value ? 1 : (value == distIpRange.value ? 0 : -1);
    }

    public String getDist() {
        return dist;
    }

    public long getValue() {
        return value;
    }

    public String getCountry() {
        return country;
    }

    public String getProvice() {
        return provice;
    }

    public String getCity() {
        return city;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "DistIpRange [" + (country != null ? "country=" + country + ", " : "") + (provice != null ? "provice=" + provice + ", " : "")
                + (city != null ? "city=" + city + ", " : "") + (dist != null ? "dist=" + dist + ", " : "") + "value=" + value + ", longitude="
                + longitude + ", latitude=" + latitude + "]";
    }

}
