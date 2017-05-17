/**
 * Copyright 2017 Goldman Sachs.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.parser.JSONParser;
//import pojo.Company;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

// TODO - add your @Path here
@Path("company")

public class CompanyResource {

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    // TODO - Add a @GET resource to get company data
    // Your service should return data for a given stock ticker
    public Response getCompanyInfo(String ticker) throws IOException {
        if(ticker == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Stock ticker empty").build();
        }
        String upperCase = ticker.toUpperCase();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("data/historicalStockData.json").getFile());
        InputStream inputStream = new FileInputStream(file);

        JsonFactory factory = new JsonFactory();
        JsonParser  parser  = factory.createParser(inputStream);

        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)) {
                String name = parser.getCurrentName();
                if(name.equals("name")){
                    //System.out.println(parser.getCurrentValue());
                    if(ticker.equals(parser.nextTextValue())){
                        System.out.println(parser.nextToken());
                        jsonToken = parser.nextToken();
                        System.out.println(parser.getText());

                        parser.nextToken();
                        System.out.println(parser.getText());
                        parser.nextToken();
                        System.out.println(parser.getText());
                        if(JsonToken.FIELD_NAME.equals(jsonToken)){
                            String dae = parser.getCurrentName();
                            System.out.println(dae);
                        }
                    }


                }
                //System.out.println(name);
            }
            // System.out.println(parser.nextToken().getCurrentName());
        }
//
//        //convert Object to JSONObject
//        JSONObject jsonObject = (JSONObject)object;
//        BufferedReader r = new BufferedReader(new FileReader(file));
//        String currLine = r.readLine();
//        while(currLine!=null){
//        //System.out.println(currLine);
//            if(currLine.contains(upperCase)){
//                System.out.println("True");
//            }
//            currLine = r.readLine();
//        }


        return null;

    }

    public static void main(String[] args) throws IOException {
        CompanyResource x = new CompanyResource();
        x.getCompanyInfo("GOOG");
    }



}
