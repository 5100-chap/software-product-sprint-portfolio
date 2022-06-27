// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
//Google Cloud Datastore Libraries
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
//Java HTTP Libraries
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//Jsoup Libraries
import org.jsoup.Jsoup;
//Import lIbraries fot TimeUnit class
import java.util.concurrent.*;
@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name-input");
    String email = request.getParameter("email-input");
    String description = request.getParameter("message-input");
    long timestamp = System.currentTimeMillis();
    TimeUnit stime = TimeUnit.SECONDS;
    long timeToSleep = 3L;
    // Write the input to the response so the user can see it.
    response.setContentType("text/html;");
    response.getWriter().println("Your message has been registered!");
    response.getWriter().println("<p>Name: " + name + "</p>");
    response.getWriter().println("<p>Color: " + email + "</p>");
    response.getWriter().println("<p>Description: " + description + "</p>");
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");
    FullEntity taskEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("Name_Sender", name)
            .set("Email_Sender", email)
            .set("Message_Sender", description)
            .set("timestamp", timestamp)
            .build();
    datastore.put(taskEntity);
    try{
      stime.sleep(timeToSleep);
      response.getWriter().println("<p>Returning to Contact Me page...</p>");
      response.sendRedirect("contact.html");
    }catch(InterruptedException x){
      response.getWriter().println("<p>You can go back to Portfolio's page</p>");
    }
  }
}
