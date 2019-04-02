package com.bootdo_jpa.common.utils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.bootdo_jpa.common.annotation.MustConvert;

public class BizUtil {

    public static <E> E copyProperties(E dest, E orig) {

        try {
            Field[] fields = orig.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object value = parGetVal(fieldName, orig);
                if (field.isAnnotationPresent(MustConvert.class)) {
                    parSetVal(fieldName, dest, value);
                } else {
                    if (value != null) {
                        parSetVal(fieldName, dest, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dest;
    }

    /**
     * 通过get方法获取属性值
     */
    public static Object parGetVal(String fieldName, Object obj) {

        Object fieldVal = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method method = pd.getReadMethod();
            fieldVal = method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
        return fieldVal;
    }

    /**
     * 通过Set方法赋属性值
     */
    public static void parSetVal(String fieldName, Object obj, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(obj, value);
        } catch (Exception e) {

        }

    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String getBidCode(String province, int id) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(new Date());
        return  province + dateString + id + getRandomString(6);
    }


    /**
     * 获取年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }


    /**
     * 获取Map 升序 字符串
     * @param map
     * @return
     */
    public static String getSortMap(Map<String,String[]> map)
    {
        Map<String, String[]> sortedMap = new TreeMap<String, String[]>(map);
        Set<String> keySet = sortedMap.keySet();
        Iterator<String> iter = keySet.iterator();

        StringBuffer sb = new StringBuffer();

        while (iter.hasNext()) {
            String key = iter.next();

            sb.append(key + "=" + sortedMap.get(key)[0] + "&");
        }

        String sortStr = sb.toString();
        if (sortStr.length() > 0)
        {
            sortStr = sortStr.substring(0,sortStr.length()-1);
        }
        return sortStr;
    }

    /**
     * 根据日期取得星期几
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];

    }

        /**
         * 根据经纬度和半径计算经纬度范围
         *
         * @param raidus 单位米
         * @return minLat, minLng, maxLat, maxLng
         */
    public static double[] getAround(double lat, double lon, int raidus) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }


    /**
     * 随机数组取值
     *
     * @param array
     * @param count
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getRandomFromArray(List array, int count) {
        List result = new ArrayList<>();
        boolean r[] = new boolean[array.size()];
        Random random = new Random();
        int m = count; // 要随机取的元素个数
        if (m > array.size() || m < 0 || m == array.size()) {
            return array;
        }
        int n = 0;
        while (true) {
            int temp = random.nextInt(array.size());
            if (!r[temp]) {
                if (n == m) // 取到足量随机数后退出循环
                    break;
                n++;
                result.add(array.get(temp));
                r[temp] = true;
            }
        }
        return result;
    }

    /**
     * 获取话题的tid
     * @param topicId
     * @return
     */
    public static String getTopicId(Integer topicId)
    {
        return getMD5Str("Topic"+topicId+"PPBody");
    }

    public static String getTribeId(Integer tribeId)
    {
        return getMD5Str("Tribe"+tribeId+"PPBody");
    }

    /**
     * md5加密
     *
     * @param md5
     * @return
     */
    public static String getMD5Str(String md5) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(md5.getBytes());
        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        String serverToken = hexString.toString().toUpperCase();
        return serverToken;

    }

    /**
     * 生成随机的字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXWZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机指定位数数字
     *
     * @param length 随机几位数
     * @return
     */
    public static String getRandomNum(Integer length) {
        StringBuilder str = new StringBuilder(); //定义变长字符串
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        //将字符串转换为数字并输出
        return str.toString();
    }

    /**
     * 生成16位带日期随机的订单号
     *
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static String getOutTradeNo() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String today = df.format(new Date());
        String outTradeNo = "SS" + today + getRandomString(6);
        return outTradeNo;
    }


    /**
     * 根据日期获取指定月后日期
     *
     * @param date   日期
     * @param months 月数 3后三个月日期 -3前三个月
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static Date getDayByMonths(Date date, Integer months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }



    /**
     * 获取两个日期之间的天数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static Integer getDaysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = 0;
        if (compareDate(date1, date2) == 1) { //date1在dete2之后
            between_days = (time1 - time2) / (1000 * 3600 * 24);
        } else {
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 指定日期+天数后的日期
     *
     * @param date 日期
     * @param days 天数 -3三天前 3三天后
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static Date getDayByDays(Date date, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
        return cal.getTime();
    }


    /**
     * 获取某天的开始
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }

    /**
     *  判断选择的日期是否是本周
     */
    public static boolean isThisWeek(long time)
    {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if(paramWeek==currentWeek){
            return true;
        }
        return false;
    }

    /**
     * 判断选择的日期是否是今天
     * @param time
     * @return
     */
    public static boolean isToday(long time)
    {
        return isThisTime(time,"yyyy-MM-dd");
    }

    /**
     * 判断选择的日期是否是本月
     * @param time
     * @return
     */
    public static boolean isThisMonth(long time)
    {
        return isThisTime(time,"yyyy-MM");
    }

    private static boolean isThisTime(long time,String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if(param.equals(now)){
            return true;
        }
        return false;
    }

    /**
     * 日期比较前后
     *
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static Integer compareDate(Date date1, Date date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String dt1 = df.format(date1);
            String dt2 = df.format(date2);
            if (df.parse(dt1).getTime() > df.parse(dt2).getTime()) {
                System.out.println(Thread.currentThread().getName()+"-----"+dt1 + "在" + dt2 + "后");
                return 1;
            } else if (df.parse(dt1).getTime() < df.parse(dt2).getTime()) {
                System.out.println(Thread.currentThread().getName()+"-----"+dt1 + "在" + dt2 + "前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据指定时间获取前后hours小时时间
     *
     * @param lookTime 带看时间
     * @param hours    -2(前2小时)  2(后2小时)
     * @return
     * @author bootdo-jpa huyidao---123@163.com
     * @date 2019-04-01 10:19:23
     */
    public static Date getTimeByHours(Date lookTime, Integer hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(lookTime);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + hours);
        return cal.getTime();
    }


    /**
     * 火星坐标gcj02ll–>百度坐标bd09ll
     *
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * 百度坐标bd09ll–>火星坐标gcj02ll
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    public static String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 日期字符串转Date
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strDate) throws ParseException {
        if (strDate.contains("-")) {
            SimpleDateFormat formatter;
            if (strDate.length() == 16) {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            } else if (strDate.contains(":")){
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }else {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            }
            Date dtDate = formatter.parse(strDate);
            return dtDate;
        } else if (strDate.matches("^\\d+$")) {
            Long lDate = new Long(strDate);
            return new Date(lDate);
        }else{
            return null;
        }
    }
}

