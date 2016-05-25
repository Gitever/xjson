/**
 * 
 * File Name: FormatJack.java 
 * Package Name: com.x.xjson
 * Date: 2016年5月24日 上午9:59:51
 */
package com.x.xjson;

/**
 * @author x
 *
 */
public class FormatJack {
	public static void main(String[] args) {
		String base64 = "eyJpZDM0OTQiOiB7ImR1dHlfbGlzdCI6IFsiZHV0eS10eXBlNyIsICJkdXR5LXR5cGU0IiwgImR1dHktdHlwZTUiLCAiZHV0eS10eXBlMiIsICJkdXR5LXR5cGUxIiwgImR1dHktdHlwZTYiLCAiZHV0eS10eXBlOCIsICJkdXR5LXR5cGUzIiwgImR1dHktdHlwZTkiXSwgImR1dHlfdHlwZSI6ICJtb3JlIiwgImRvY3Rvcl9uYW1lIjogIuaigemUkCIsICJwaG9uZV9udW1iZXIiOiAiMTM1NTQ4NzU0ODEifSwgImlkMzc3MiI6IHsiZHV0eV9saXN0IjogW10sICJkdXR5X3R5cGUiOiAiNSIsICJkb2N0b3JfbmFtZSI6ICLmnY7lt40iLCAicGhvbmVfbnVtYmVyIjogIjE3NzA5ODc3NTc3In0sICJpZDM3ODciOiB7ImR1dHlfbGlzdCI6IFtdLCAiZHV0eV90eXBlIjogIjQiLCAiZG9jdG9yX25hbWUiOiAi5p2o5pil5piOIiwgInBob25lX251bWJlciI6ICIxNzcwOTg3MDk5MCJ9LCAiaWQ1NTI4IjogeyJkdXR5X2xpc3QiOiBbXSwgImR1dHlfdHlwZSI6ICI5IiwgImRvY3Rvcl9uYW1lIjogIumai+WQieagiyIsICJwaG9uZV9udW1iZXIiOiAiIn0sICJpZDYwNzciOiB7ImR1dHlfbGlzdCI6IFtdLCAiZHV0eV90eXBlIjogIjMiLCAiZG9jdG9yX25hbWUiOiAi5byg5bOwIiwgInBob25lX251bWJlciI6ICIifX0=";
		String jsonStr = "{\"id\":\"1\",\"name\":\"a1\",\"obj\":{\"id\":11,\"name\":\"a11\",\"array\":[{\"id\":111,\"name\":\"a111\"},{\"id\":112,\"name\":\"a112\"}]}}";
		jsonStr = Base64Util.decode(base64);
				
		Province province = new Province();
        province.name = "Shanxi";
        province.population = 37751200;
        province.time = "2016/12/03 12:12:50";
        
        JsonUtil.objToJsonPath(province, "country.json");
		JsonUtil.objToXmlPath(province, "country.xml");
        System.err.println(JsonUtil.objToXml(province));
        
        Province p1 = (Province) JsonUtil.jsonToObject("{\"name\" : \"Shanxi\",\"population\" : 37751200}", Province.class);
        System.out.println(p1);
	}
	
	public static class Province {
	    public String name;  
	    public int population;
		public String time;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Province [name=" + name + ", population=" + population + ", time=" + time + "]";
		}
	}
}