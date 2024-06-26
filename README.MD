# FAKE OAUTH2 SERVER
# ! USE this project ONLY for TEST purpose !

All secret inside are exposed to allow clients to have full access
on all resources.

We actally implement only RSA asym algorithm.


## First setup

We use this commands to generate all openssl keys to generate token

PRIVATE : 
```shell
openssl genrsa -out private_key.pem
```

PUBLIC :
```shell
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

PUBLIC in PKCS8 :
```shell
openssl pkcs8 -topk8 -in private_key.pem -inform pem -out private_key_pkcs8.pem -outform pem -nocrypt
```

JWKS from PUBLIC :
```json
{
  "kty": "RSA",
  "n": "005OYGd6munpIw9riUhrhFy73t0I3f01HJHecXIFCBSOUKntRW6I_RXpI7WM4Vynrh8Or0CWumb7jNgkkeWKHBLc6Apwrl-3hM5pT9iK4M3IPn1OIDzfI4CAH3QMZ69M4p0lX13Zp8PEGTaxWP4HIxgDxXh_Lbee80GdzRFEFyL39irwuqmPvw5RUOT2Kbqg6l8rYV7b7dlDBSh9XoJ2792ew-Lhe9OWzGiqCF0n676ma81ojXY4KVdBMg6nry6WTGwSMuYIYlPj3zL6QX2CQWgu6naYiwAc8wzyJk1YLBnohTlFlg4Aqe7zGZ4Po0s52BQuHAYwCiclxGrrsDhNyun97UZg71_4IMWeZ6HEw16WdcvajxUTedJcW0NDBxNtqd7a8hkE23gxVIc-kj9qtffsdJkF-yK93W4I_492C38diT6xfYFNbiYn4WXzpHzsR-kHDuDeIiI_5XtqhaJiVFmYuScM-mA_Xjm37Twrw3N7_NpYQLnvYg1hwaBl1gZhJrlyC0pHfPNw3_7kni_B1Rr1rS51LZMjDlz5-WaCWPc8BB4_qyZ7CL2R_eMbNVti1zzet3MeKbF-2Lh6Qe4SQv9S9H8F3XZ9s7fr5qPDThj1Jphda6M6f50idBVR1lbG_gppGyPGqSNw1geFr-zUuto-N9hBnZ4d1HSEw4rkkt0",
  "e": "AQAB",
  "alg": "RS256",
  "kid": "MAIN",
  "use": "sig"
}
```

## How it works

This tool generate a jwt and serve it with also all keys.

Actually the only protocol implemented is RSA but you can implement wich protocl you want.

All the propriety of the payload of the access_token, id_token and user can be managed with the CRUD API configurations.
All data are saved in-memory with a static instance.
You can find all the CRUD api and test it in the Bruno Api Collection below.

### IMPORTANT

The full flow must be :
1) generate the configurations
2) start OAuth flow -> all tokens will respect the configurations setted at point 1
3) generate new configurations
4) All new tokens will take the configurations of step 3

So, the order to get token is important if you want to use multiple users.

## Api Documentation

You can use Bruno to open our API documentation to start exploring the project : https://www.usebruno.com/

## Use DOCKER IMAGE 

See all tags here : https://hub.docker.com/r/acando14/fake-oauth2

OR 

```shell
docker pull acando14/fake-oauth2

docker run -it -p 8080:8080 acando14/fake-oauth2
```


