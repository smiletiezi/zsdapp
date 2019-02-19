package com.py.zsdApp.utils;

public class AlipayConfig {
	  // 1.商户appid
    public static String APPID = "2018112762319918";    
    
    // 2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDK++VVbIUymJEQstXy5kfj64Q/2dLuQ8Lg6Gl9k6NLqK8NgtTDfNWW25dgrNMKaMadD5Y+4dlW1FnxflrRwq82gj3FDBcWycPdBR5Ufb4s6SrXciEAkK9EWTSCUQ4tdyipzCg4aCoJjtsdPXGbyKyxLBJiAQVn1zuujUf2Df9AZyfN6BKac+eCBr8h8H6RU5VxQzn5jLwjXWjDQ4FezuHVGduJF7D66O6XMeOm717+4rOokwsnRC1fQhbd615+36QdHINev6FIiUxTlAueNduzZy6fdMaISwsAGP7vZrBhOb4D7Bhm7fS+tkUTs+SnLruKvbjYGDHgyOIOSOKmajDRAgMBAAECggEAfJCcqhGQo/4Qd+jndspur6XmYLCxytn2i8LUieXCPjnkQyxYmSbQhGV5vjAQysCFsfjQwQIkOC7Ui2h5H2ST1aWTdor22Fs1QTtU45DO1gy458H4Erq3dAAyQw6u3qPTZnAM9qClu3vZZ1Y/k9RQdeGP3mi2AoWI8JNu58arL7+Ep8PE456y7YNfH6xwzwb4ojynQpr7rifYdPAXG+LFK85WkRNVLIKEcvp5P7vUL7Ui+vpPLnw+rvSWtJIVEDAGJoCUSIy2hIeRjqQHFoyyMXJVsVQ07DbC1MZC+v/PQI0hCU2dOFghcAowOM9u1H+aQgRnLK4QazROfB0TzbwKKQKBgQD+hUdAH5MsMwxOWPKPo0OlDuLS8zvBHN1Yhk3BmVLZr9bzOYFcuNSr/amXkHudtPLxxyH2Nx8bHpatjdD3aFg+Jh2u3HQwqbhO1E+im6QdBJYNowmAlUD1G97jfFEijdCSpqUMsphNU53WD4Cz8gQjFkhUcuJe6e98zmr04fA7ZwKBgQDMKe6W4Q5xn1G+O0cHiqZ3zg6Cc34Yl2PzSKFlTsw3Guob6Wy2DkR6RiDVg26gBtM1/vFuTUxjhGnwocpeHHeY8pYDkR6YjGUFc9M2FXnEtBwHT3ypYrOgD1NuE3JfEGAVFUGbPg782jkwGGfYrQdddrHn69Qiks6w56WB2ElHBwKBgQCA1sitjg47BpBJLL+EtHmumS166YOrCsiFYl8EDHAjH97oeoigfjOx6JMr1QiuWYXNnCb18ws8+CwAKNTKGLjqpPLLrG7on1FcHh89yhm2Urb+qjRKMcOTEAuxDiIoIq+dPNw55EIyioxQasKU1IMBm+r7TLiIoVUI0ZjU43ZvKwKBgH8C+yYsHrM6/TyOXhLoA8X2j/pB5UlhLVIVdiqzScJiYRA6OGJOH/W9mQGbgX/WnyExWHtZZVNn9gw4as//f7vL9t2DM3iNpu8sblJwK8/eiK1XmuM5vyutPSb2vCk947hZ//WXNvXAwOEV3czjX/iaNfVlNhTIJgPSKJnNt1CTAoGAegO6NZQO2hTqZ26cCUrx7F2uTzEZqqeqqMssc0Qldsz6nLxK45hSWaniEUYJq/dnX5qB2obHdbnPM1B2TA/VBghN+VxXiVB+tGv0fxylv4+Jah+2UuNbj6a6QYZCffr/ctlzYr25sFUwDi8+9cr0fw4nL7Zx3eRhAA6Qq/oEYTI=";
    
    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyvvlVWyFMpiRELLV8uZH4+uEP9nS7kPC4OhpfZOjS6ivDYLUw3zVltuXYKzTCmjGnQ+WPuHZVtRZ8X5a0cKvNoI9xQwXFsnD3QUeVH2+LOkq13IhAJCvRFk0glEOLXcoqcwoOGgqCY7bHT1xm8issSwSYgEFZ9c7ro1H9g3/QGcnzegSmnPngga/IfB+kVOVcUM5+Yy8I11ow0OBXs7h1RnbiRew+ujulzHjpu9e/uKzqJMLJ0QtX0IW3eteft+kHRyDXr+hSIlMU5QLnjXbs2cun3TGiEsLABj+72awYTm+A+wYZu30vrZFE7Pkpy67ir242Bgx4MjiDkjipmow0QIDAQAB";
    
    //(转账)网关地址
  	public static final  String gateway_url = "	https://openapi.alipay.com/gateway.do";
    
    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://47.112.25.212:80/zsdApp/alipay/alipayNotify";
    
    // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://39.108.239.193:8080/alipay/return_url";
    
    // 6.请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";    
    //沙箱支付网关
    public static String sandbox_url = "https://openapi.alipaydev.com/gateway.do";
    // 7.编码
    public static String CHARSET = "utf-8";
    
    // 8.返回格式
    public static String FORMAT = "json";
    
    // 9.加密类型
    public static String SIGNTYPE = "RSA2";
    
    public static boolean falg = true;
}

