package com.bpal.weatherapp.Model;

import org.json.JSONObject;

public class WeatherData {

    private String temperature, city, condition, icon, max_temp, min_temp, humidity, pressure;

    public static WeatherData fromJson(JSONObject jsonObject) {
        try {

            WeatherData weatherData = new WeatherData();

            String country = jsonObject.getJSONObject("sys").getString("country");
            String City = jsonObject.getString("name");
            weatherData.city = weatherData.setCity(City+", "+country);

            weatherData.condition = String.valueOf(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
            String id = String.valueOf(jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id"));

            double temp = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
            int result = (int) Math.rint(temp);
            weatherData.temperature = String.valueOf(result);
            weatherData.icon = updateIcon(Integer.parseInt(id));

            weatherData.humidity = String.valueOf(jsonObject.getJSONObject("main").getInt("humidity"));
            weatherData.pressure = String.valueOf(jsonObject.getJSONObject("main").getInt("pressure"));

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String updateIcon(int condition) {
        if (condition>=200 && condition<=299) {
            return "thunderstorm";
        }
        else if (condition>=300 && condition<=599) {
            return "rain";
        }
        else if (condition>=600 && condition<=699) {
            return "snow";
        }
        else if (condition>=700 && condition<=799) {
            return "mist";
        }
        else if (condition>=800 && condition<=804) {
            return "cloud";
        }
        else if (condition==800) {
            return "sun";
        }
        return "sun";
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getTemperature() {
        return temperature+"°";
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public String setCity(String city) {
        this.city = city;
        return city;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
