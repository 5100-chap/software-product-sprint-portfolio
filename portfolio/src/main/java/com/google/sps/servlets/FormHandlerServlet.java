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
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Message Variables
    String name = request.getParameter("name-input");
    String email = request.getParameter("email-input");
    String description = request.getParameter("message-input");
    // Timestamp
    LocalDateTime timefor = LocalDateTime.now();
    DateTimeFormatter timeunf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z");
    String timestamp = timefor.format(timeunf);
    // Time sleep
    TimeUnit stime = TimeUnit.SECONDS;
    long timeToSleep = 0L;
    try {
      //Calls Google's Datastore
      Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
      KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");
      //Creates a entity in the database
      FullEntity taskEntity = Entity.newBuilder(keyFactory.newKey())
          .set("Name_Sender", name)
          .set("Email_Sender", email)
          .set("Message_Sender", description)
          .set("timestamp", timestamp)
          .build();
      //Inserts the entity in the database
      datastore.put(taskEntity);
      //Returns success page
      stime.sleep(timeToSleep);
      response.sendRedirect("/formResponse/success.html#yes");
    } catch (InterruptedException x) {
      //Returns error page
      response.sendRedirect("/formResponse/Error.html#notSubmitted");
    }
  }
}
