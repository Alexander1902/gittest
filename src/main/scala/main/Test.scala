package main


import http.HttpGetData
import java.util.HashMap

object Test {
  def main(args: Array[String]): Unit = {
    val map:HashMap[String,String] =new HashMap[String,String]()
    map.put("domain", "v.hebtv.com")
    val str:String = HttpGetData.httpClientGet("http://116.211.90.85:9995/api/v1/business/domain/", map)
    print(str)
  }
}
