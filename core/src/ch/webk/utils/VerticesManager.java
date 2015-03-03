package ch.webk.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class VerticesManager {

    private static Logger l = new Logger("VerticesManager", true);

    public static ArrayList<Vector2[][]> vertices = new ArrayList<Vector2[][]>();
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();

	public static Vector2[][] getVertices(String type, String json, float width, float height) {
		String mapIndex = type + "_" + width + "_" + height;
		int index = -1;
		try {
			index = map.get(mapIndex);
		} catch (NullPointerException e) {
			index = -1;
		}
		if (index >= 0) {
			return vertices.get(index);
		} else {
			final int pos = map.size();
			map.put(mapIndex, pos);
			vertices.add(pos, createVertices(json, width, height));
			return getVertices(type, json, width, height);
		}
	}
	
	private static Vector2[][] createVertices(String json, float width, float height) {
		json = json.replaceAll("\\[\\[", "");
		json = json.replaceAll("\\]\\]", "");
		json = json.replaceAll("...:", "");
        float scaleX = Constants.WORLD_TO_SCREEN / width;
        float scaleY = Constants.WORLD_TO_SCREEN / height;
		String[] vector = json.split("\\],\\[");
		Vector2[][] vertices = new Vector2[vector.length][];
		int i = 0;
        float xMin = Float.MAX_VALUE;
		float xMax = -Float.MAX_VALUE;
		float yMin = Float.MAX_VALUE;
        float yMax = -Float.MAX_VALUE;
		for (String v : vector) {
            float xMaxTemp = getMaxX(v);
			if (xMaxTemp > xMax) { xMax = xMaxTemp; }
            float yMaxTemp = getMaxY(v);
			if (yMaxTemp > yMax) { yMax = yMaxTemp; }
			i++;
		}
		i = 0;
		for (String v : vector) {
			vertices[i] = getVector2(v, scaleX, scaleY, xMax/2, yMax/2);
			i++;
		}
		return vertices;
	}

	private static float getMaxX(String input) {
		String[] v_strarray = input.split("\\},\\{");
		float xMax = 0;
		for (String v_str : v_strarray) {
			v_str = v_str.replaceAll("\\{", "");
			v_str = v_str.replaceAll("\\}", "");
			String[] xy = v_str.split(",");  
			if (xMax < Float.parseFloat(xy[0])) { xMax = Float.parseFloat(xy[0]); }
		}
		return xMax;
	}
	
	private static float getMaxY(String input) {
		String[] v_strarray = input.split("\\},\\{");
		float yMax = 0;
		for (String v_str : v_strarray) {
			v_str = v_str.replaceAll("\\{", "");
			v_str = v_str.replaceAll("\\}", "");
			String[] xy = v_str.split(",");  
			if (yMax < Float.parseFloat(xy[1])) { yMax = Float.parseFloat(xy[1]); }
		}
		return yMax;
	}

	
	private static Vector2[] getVector2(String input, float scaleX, float scaleY, float xMax, float yMax) {
		String[] v_strarray = input.split("\\},\\{");
		Vector2[] v = new Vector2[v_strarray.length];
		int i = 0;
		float sX = 1;
		float sY = 1;
		if (xMax < yMax) { sX = yMax / xMax; }
		else { sY = xMax / yMax; }
		for (String v_str : v_strarray) {
			v_str = v_str.replaceAll("\\{", "");
			v_str = v_str.replaceAll("\\}", "");
			String[] xy = v_str.split(",");
		    v[i] = new Vector2((Float.parseFloat(xy[0]) - xMax) / scaleX * sX, (Float.parseFloat(xy[1]) - yMax) / scaleY * sY);
		    i++;
		}
		return v;
	}

}