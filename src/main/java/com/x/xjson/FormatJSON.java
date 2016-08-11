package com.x.xjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FormatJSON {
	public static void main(String[] args) {
		String base64 = "eyJpZDM0OTQiOiB7ImR1dHlfbGlzdCI6IFsiZHV0eS10eXBlNyIsICJkdXR5LXR5cGU0IiwgImR1dHktdHlwZTUiLCAiZHV0eS10eXBlMiIsICJkdXR5LXR5cGUxIiwgImR1dHktdHlwZTYiLCAiZHV0eS10eXBlOCIsICJkdXR5LXR5cGUzIiwgImR1dHktdHlwZTkiXSwgImR1dHlfdHlwZSI6ICJtb3JlIiwgImRvY3Rvcl9uYW1lIjogIuaigemUkCIsICJwaG9uZV9udW1iZXIiOiAiMTM1NTQ4NzU0ODEifSwgImlkMzc3MiI6IHsiZHV0eV9saXN0IjogW10sICJkdXR5X3R5cGUiOiAiNSIsICJkb2N0b3JfbmFtZSI6ICLmnY7lt40iLCAicGhvbmVfbnVtYmVyIjogIjE3NzA5ODc3NTc3In0sICJpZDM3ODciOiB7ImR1dHlfbGlzdCI6IFtdLCAiZHV0eV90eXBlIjogIjQiLCAiZG9jdG9yX25hbWUiOiAi5p2o5pil5piOIiwgInBob25lX251bWJlciI6ICIxNzcwOTg3MDk5MCJ9LCAiaWQ1NTI4IjogeyJkdXR5X2xpc3QiOiBbXSwgImR1dHlfdHlwZSI6ICI5IiwgImRvY3Rvcl9uYW1lIjogIumai+WQieagiyIsICJwaG9uZV9udW1iZXIiOiAiIn0sICJpZDYwNzciOiB7ImR1dHlfbGlzdCI6IFtdLCAiZHV0eV90eXBlIjogIjMiLCAiZG9jdG9yX25hbWUiOiAi5byg5bOwIiwgInBob25lX251bWJlciI6ICIifX0=";
		String jsonStr = "{\"id\":\"1\",\"name\":\"a1\",\"obj\":{\"id\":11,\"name\":\"a11\",\"array\":[{\"id\":111,\"name\":\"a111\"},{\"id\":112,\"name\":\"a112\"}]}}";
		jsonStr = Base64Util.decode(base64);
		
		String fotmatStr = FormatJSON.format(jsonStr);
		System.out.println(fotmatStr);
		// http://10.111.10.19:8080/xunie/my.sl?txtName=admin
		
		ObjectMapper mapper = new ObjectMapper();  
	    ObjectNode root1 = mapper.createObjectNode();
	    
		String value = "null";
		root1.put("key", value);
		System.out.println(root1);
	}

	public static String format(String jsonStr) {
		//jsonStr = jsonStr.replace(" ", "");
		jsonStr = jsonStr.replace("\\\"", "\"");
		jsonStr = jsonStr.replace("\\\\", "");
		if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                	if ("]".equals(String.valueOf(jsonStr.charAt(i+1)))) {
                		sb.append("[]");
	                    i++;
					}else{
						sb.append(current);
	                    sb.append('\n');
	                    indent++;
	                    addIndentBlank(sb, indent);
					}
                    break;
                case '}':
                case ']':
                	sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                case ':':
            		if (last == '"') 
            			sb.append(current + " ");
					else
						sb.append(current + "");
                	break;
                default:
                    sb.append(current);
            }
        }        
        return sb.toString();
	}
	
	/**
	 * 换新行时，添加tab键
	 * @param sb
	 * @param indent
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("    ");
        }
    }
}
