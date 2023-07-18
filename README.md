
![Logo](https://i.imgur.com/wOUxPX3.png)


# ⚽Football Appointment Aplication

 ❔ **What is this aplication about?**

 This aplication offers a secure and simple experience for the client, such as creating an account, logging in with the created account, the posibility to view all the dates and places available provided by the respective football pitch, a nice description and adress.

 After the appointment has been made, the user can view it in the "Appointment" page, and if this client no longer wishes to have the appointment, he can delete it, and the same applies to the account where you can also update the account.



 

## 🛠️  How it was built?
🌔 This project is built 100% in **Java**, with the help of **Eclipse IDE**.

💾 For starters, the information found in the project comes from a relational database made in MySQL and all data can be updated from this database. All the information from the project were provided from the database. The schema contains 4 tables, linking together a **one-to-many** relationship and a **many-to-many** relationship. Here is the EER Model for the database:

![Database Scheme](https://i.imgur.com/QGF7Jo9.png)

🔗 After, the connection between Eclipse and MySQL was made with **EclipseLink** which helped me, together with converting the normal project to a **JPA project** and generating the **"persistence.xml"** file, to generate entities from tables with **JPA Tools**.

🖼️ The design was made in JavaFX, it is pretty simple and it is a user-friendly design, simple to navigate between pages, with sugestive names for every button, the application contains 5 different scenes: **Login Page, Register Page, Menu Page, Account Page and Appointment Page** with multiple Buttons, Text Fields, Labels, Images and Choice Boxes. It also have alert boxes when something happens, to let the user know what happened and if it succeded.
## Features

- It provides a foundation for different appointment apps
- Register/Login page with working connection to a database
- Live dates that can be updated from database
- Updating or deleting the user account
- Unique usernames
- Secure connection


## Demo

Insert gif or link to demo


## Deployment

To deploy this project run

```bash
  npm run deploy
```


## Usage/Examples

```javascript
import Component from 'my-project'

function App() {
  return <Component />
}
```


## Author

👨‍🎓 [@clauf14](https://www.github.com/clauf14)


## Tech Stack

**Client:** React, Redux, TailwindCSS

**Server:** Node, Express

