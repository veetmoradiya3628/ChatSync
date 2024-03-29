<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Chat Sync Application</h3>

  <p align="center">
    An Real Time browser based application.
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#technolgy-used">Technologies</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#database-design">Database Design</a></li>
    <li><a href="#application-architecture">Application Architecture</a></li>
    <li><a href="#websocket-message-flows">Websocket Message flows</a>
        <ol>
            <li><a href="#one-to-one-message">One to One Message</a></li>
            <li><a href="#group-message">Group Message</a></li>
        </ol>
    </li>
    <li><a href="#websocket-events-screenshots">Websocket Events Screenshots</a></li>
    <li><a href="#application-screenshots">Application Screenshots</a>
        <ol>
            <li><a href="#login-registration">Login & Registration</a></li>
            <li><a href="#profile">Profile</a></li>
            <li><a href="#contacts">Contacts</a></li>
            <li><a href="#groups">Groups</a></li>
            <li><a href="#chats">Chats</a></li>
            <li><a href="#logout">Logout</a></li>
        </ol>
    </li>
    <li><a href="#future-enhancements">Future Enhancements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
Chat sync is a simple yet powerful tool for real-time communication. It allows users to connect with each other over the internet and exchange messages seamlessly. Whether you're looking to chat with friends, colleagues, or customers, our application provides a reliable platform for communication.

<p align="right">(<a href="#top">back to top</a>)</p>



### Technolgy Used

The following technologies and tools have been equipped to develop this project -

* Spring Boot - Spring Data JPA, Spring Web Socket
* Angular
* MySQL

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.



* java - jdk 21 and above
* npm 
  ```sh
  npm install npm@latest -g
  ```
* MySQL
* Run Angular Application with 
    ```
    ng serve
    ```
* Run Spring Boot application with 
    ```
    ./mvnw
    ```
<p align="right">(<a href="#top">back to top</a>)</p>




<!-- Features -->
## Features

* **Real-time Messaging:** Send and receive messages instantly without any delay.
* **User Authentication:** Secure login system to ensure only authorized users can access the chat.
* **User Profiles:** Customize your profile with a username and avatar.
* **Group Chats:** Create or join group conversations with multiple participants.
* **Message History:** View past messages for context and reference.
* **File Sharing:** Share files such as images, documents, etc., within the chat.
* **Notifications:** Receive notifications for new messages and mentions.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Database design -->
## Database Design

![Database Design Diagram](/screen_shots/db_design.png)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Application Architecture -->
## Application Architecture
![Application Arch. Diagram](/screen_shots/application_arch.jpg)
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Message flows -->
## Websocket Message Flows

### One To One Message
![One to One Message Seq. Diagram](/screen_shots/one_to_one_seq.jpg)

### Group Message
![Group Message Seq. Diagram](/screen_shots/group_msg_seq.jpg)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Websocket Event Screenshots -->
## Websocket Events Screenshots

### Websocket subscribe event
![](/screen_shots/ws_events/ws_subsribe_event.png)

### Sent One to one message event
![](/screen_shots/ws_events/sent_one_to_one_msg.png)

### Sent Group message event
![](/screen_shots/ws_events/sent_grp_msg.png)

### One to one message sent confirm received event
![](/screen_shots/ws_events/receive_one_to_one_msg_confirm.png)

### Receive one to one msg event
![](/screen_shots/ws_events/receive_one_to_one_txt_msg.png)

### Receive group msg event
![](/screen_shots/ws_events/receive_grp_text_msg.png)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Application Screenshots -->
## Application Screenshots

### Login Registration

#### Registration
![Registration Page](/screen_shots/UI/registration_page.png "Registration Page - This page allows users to register for an account.")

#### Login
![Login Page](/screen_shots/UI/login_page.png "Login Page - Users can log in using their registered credentials on this page.")

#### Activation Request
![Activation Request Page](/screen_shots/UI/activate_req_page.png "Activation Request Page - This page is used to request activation for an account.")

<p align="right">(<a href="#top">back to top</a>)</p>

### Profile

![Profile page](/screen_shots/UI/profile_page.png)

<p align="right">(<a href="#top">back to top</a>)</p>

### Contacts

#### contacts tab
![](/screen_shots/UI/contacts/contact_tab.png)

#### contact groups
![](/screen_shots/UI/contacts/contact_grp_dialog.png)

#### contact chat confirm
![](/screen_shots/UI/contacts/contact_chat_confirm.png)

#### contact delete confirm
![](/screen_shots/UI/contacts/contact_delete_confirm.png)

#### global contacts tab
![](/screen_shots/UI/contacts/global_contact_tab.png)

#### global contact groups
![](/screen_shots/UI/contacts/global_contact_grps.png)

#### global contact add confirm
![](/screen_shots/UI/contacts/global_contact_add_dialog.png)

<p align="right">(<a href="#top">back to top</a>)</p>

### Groups

#### groups tab
![](/screen_shots/UI/groups/groups_tab.png)

#### group members dialog
![](/screen_shots/UI/groups/group_members_dialog.png)

#### add new member dialog
![](/screen_shots/UI/groups/add_new_member_grp_dialog.png)

#### leave group confirm
![](/screen_shots/UI/groups/leave_grp_confirm_dialog.png)

#### delete group confirm
![](/screen_shots/UI/groups/delete_grp_confirm.png)

#### new group tab
![](/screen_shots/UI/groups/default_new_grp_tab.png)

#### new group filled page
![](/screen_shots/UI/groups/filled_new_grp.png)

<p align="right">(<a href="#top">back to top</a>)</p>

### Chats

#### Chat Page default
![](/screen_shots/UI/chat_page/chat_page_default_layout.png)

#### Receive One to One Msg, Inactive thread
![](/screen_shots/UI/chat_page/receive_one_to_one_msg_inactive_thread.png)

#### Receive Grp Msg, Inactive thread
![](/screen_shots/UI/chat_page/receive_grp_message_inactive_thread.png)

#### Open Inactive Msg thread
![](/screen_shots/UI/chat_page/open_inactive_thread_with_pending_messages.png)

#### Open Inactive Grp thread
![](/screen_shots/UI/chat_page/open_inactive_grp_thread.png)

<p align="right">(<a href="#top">back to top</a>)</p>

### Logout

#### Logout confirm
![](/screen_shots/UI/logout_confirm.png)

<p align="right">(<a href="#top">back to top</a>)</p>

## Future Enhancements

<ul>
    <li>File & Image sharing support for one to one and group messaging</li>
    <li>Better UI UX design</li>
    <li>Websocket / REST APIs optimization & improvements</li>
</ul>

<p align="right">(<a href="#top">back to top</a>)</p>