# Star Wars API
Star Wars API conta com 2 usuários defaults cadastrados durante a inicialização da aplicação: 
- Yoda, perfil: Administrador, cpf: 000.000.000-01
- Vader, perfil: Usuário, cpf: 000.000.000-02

A documentação da API pode ser acessada a partir do endereço:
http://localhost:8080/swagger-ui.htm

O token JWT de autenticação é gerado a partir do endpoint: 

POST /authentication HTTP/1.1 <br/>
Host: localhost:8080 <br />
Content-Type: application/json <br />
Accept: */* <br />
Host: localhost:8080 <br />
{ <br />
	"cpf": "000.000.000-01", <br />
	"pwd": "123" <br />
} <br />

O token gerado será retornado no header "Authorization" da resposta.

Infelizmente, não foi possível implementar todos os requisitos do teste, pois o mesmo está muito extenso. Por conta disso, os seguintes requisitos ficaram por fazer: 
- Fazer uso do framework Flyway para o versionamento e
execução dos scripts do banco de dados.   - Obter todos os personagens e ordená-los por quantidade
de filmes que aparecem, usando nome do personagem
como segundo critério de ordenação (ordem alfabética).
Este endpoint poderá ser acessado pelo perfil de usuário e
administrador.
- Realiza os teste unitários da classe StarWarsController


Agradecido pela oportunidade. 


  
