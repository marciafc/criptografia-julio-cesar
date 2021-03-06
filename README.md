# Criptografia de Júlio César

Desafio realizado para ingresso no programa AceleraDev Java da [Codenation](https://codenation.dev).

## Descrição

Segundo o Wikipedia, criptografia ou criptologia (em grego: kryptós, "escondido", e gráphein, "escrita") é o estudo e prática de 
princípios e técnicas para comunicação segura na presença de terceiros, chamados “adversários”. Mas geralmente, a criptografia 
refere-se à construção e análise de protocolos que impedem terceiros, ou o público, de lerem mensagens privadas. 
Muitos aspectos em segurança da informação, como confidencialidade, integridade de dados, autenticação e não-repúdio são centrais 
à criptografia moderna. Aplicações de criptografia incluem comércio eletrônico, cartões de pagamento baseados em chip, moedas digitais, 
senhas de computadores e comunicações militares. 
Das Criptografias mais curiosas na história da humanidade podemos citar a criptografia utilizada pelo grande líder militar romano 
Júlio César para comunicar com os seus generais. Essa criptografia se baseia na substituição da letra do alfabeto avançado um 
determinado número de casas. Por exemplo, considerando o número de casas = 3:

* **Normal**: a ligeira raposa marrom saltou sobre o cachorro cansado

* **Cifrado**: d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr

Regras

* As mensagens serão convertidas para minúsculas tanto para a criptografia quanto para descriptografia.
* No nosso caso os números e pontos serão mantidos, ou seja:

  * Normal: 1a.a

  * Cifrado: 1d.d
  
Escrever programa, em qualquer linguagem de programação, que faça uma requisição HTTP para a url abaixo:

**https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=SEU_TOKEN**

O resultado da requisição vai ser um JSON conforme o exemplo:

```json
{
	"numero_casas": 10,
	"token":"token_do_usuario",
	"cifrado": "texto criptografado",
	"decifrado": "aqui vai o texto decifrado",
	"resumo_criptografico": "aqui vai o resumo"
}
```

O primeiro passo é você salvar o conteúdo do JSON em um arquivo com o nome answer.json, que irá usar no restante do desafio.

Você deve usar o número de casas para decifrar o texto e atualizar o arquivo JSON, no campo decifrado. O próximo passo é gerar um 
resumo criptográfico do texto decifrado usando o algoritmo sha1 e atualizar novamente o arquivo JSON. 
OBS: você pode usar qualquer biblioteca de criptografia da sua linguagem de programação favorita para gerar o resumo sha1 do texto decifrado.

Seu programa deve submeter o arquivo atualizado para correção via POST para a API:  

**https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=SEU_TOKEN**

OBS: a API espera um arquivo sendo enviado como multipart/form-data, como se fosse enviado por um formulário HTML, com um campo do 
tipo file com o nome answer. Considere isso ao enviar o arquivo.

O resultado da submissão vai ser sua nota ou o erro correspondente. Você pode submeter quantas vezes achar necessário, mas a API não 
vai permitir mais de uma submissão por minuto.

Exemplo de retorno com a nota:

```json
{"score": 100}
```

## Implementação

O desafio foi resolvido no formato de API RESTful. Foram criados dois endpoints: 
    
    GET /descriptografar
    
    POST /enviar-arquivo

O endpoint **/descriptografar** obtém o arquivo JSON para solucionar o desafio.

Parameters: 
 - token (String): o token do usuário
 
Resultado da requisição:
```json 
{
  "token": "token_do_usuario",
  "cifrado": "utk ul znk hommkyz vxuhrksy zngz yulzcgxk jkbkruvkxy lgik oy zngz zkinturume ingtmky xgvojre. oz oy bkxe ngxj zu yzge iaxxktz. bobkq cgjncg",
  "decifrado": "one of the biggest problems that software developers face is that technology changes rapidly. it is very hard to stay current. vivek wadhwa",
  "numero_casas": 6,
  "resumo_criptografico": "2e89f9727a5b7bc83dde4516df5d2b7d85a52e22"
} 
```

O endpoint **/enviar-arquivo** faz a submissão da solução.  

Parameters: 
 - file (MultipartFile): o arquivo JSON com a solução
 - token (String): o token do usuário 

Resultado da requisição: 
```json
{
  "score": 100
}   
```

Para fins de estudo, o desafio foi implementado com **RestTemplate** e com **Feign**. Para separar as diferentes implementações, foi utilizado o recurso de versionamento:

RestTemplate:

    GET /v1/descriptografar
    
    POST /v1/enviar-arquivo    
 
Feign:

    GET /v2/descriptografar
    
    POST /v2/enviar-arquivo
 
## Tecnologias

 - Java
 - Spring Boot

## Referências

  - [Caesar cipher: Encode and decode online](https://cryptii.com/pipes/caesar-cipher)
  
  - [SHA1 online](http://www.sha1-online.com)
  
  - [Tabela ASCII](https://web.fe.up.pt/~ee96100/projecto/Tabela%20ascii.htm)
  
  - [Uploading a file with a filename with Spring RestTemplate](https://medium.com/red6-es/uploading-a-file-with-a-filename-with-spring-resttemplate-8ec5e7dc52ca)
  
  - [Sending multipart requests using Spring Boot and Feign](https://medium.com/comsystoreply/sending-multipart-requests-using-spring-boot-and-feign-20d5602d0f21)
  
  - [File Uploading with Open Feign](https://howtoprogram.xyz/2016/12/29/file-uploading-open-feign)