<h1>Luizalabs backend teste</h1>

<h3>Microsserviço Wishlist</h3>

<h4> 1. O que deve ser feito?</h4>

O objetivo é que você desenvolva um serviço HTTP resolvendo a funcionalidade de Wishlist do cliente. Esse serviço deve atender os seguintes requisitos:
- Adicionar um produto na Wishlist do cliente;
- Remover um produto da Wishlist do cliente;
- Consultar todos os produtos da Wishlist do cliente;
- Consultar se um determinado produto está na Wishlist do
cliente;

<h4> 2. Orientações: </h4>

Imagine que esse serviço fará parte de uma plataforma construída em uma arquitetura baseada em micro-serviços. Portanto não se preocupe com a gestão das informações de Produtos e/ou Clientes, coloque sua energia apenas no serviço da Wishlist.

É importante estabelecer alguns limites para proteger o bom funcionamento do ecossistema, dessa forma a Wishlist do cliente deve possuir um limite máximo de 20 produtos.

Tecnologias abordadas:
- Java (versão 11);
- Spring Boot (versão 2.7.1);
- MongoDB;
- Maven; 
- Clean code;
- JUnit;



Fluxograma simples para facilitar o entendimento:
![image](https://user-images.githubusercontent.com/34377631/179049028-e0f6c39e-2111-4b7c-a17c-90abef99b0da.png)


Para testes, após subir a aplicação localmente, esse é o caminho do swagger:
<br>
http://localhost:8080/swagger-ui/#/
