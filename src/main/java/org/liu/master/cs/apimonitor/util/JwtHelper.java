package org.liu.master.cs.apimonitor.util;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;

/**
 * JWT utility/helper class which provides Basic Jwt Operations
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 */
public class JwtHelper {

    private static JWTAuth jwtAuth;

    public static JWTAuth getJWtProvider(Vertx vertx){
       if(jwtAuth == null){
           return init(vertx);
       }
       else {
           return jwtAuth;
       }
    }

    /**
     * Iniliaze the JWTAuth Object
     * @param vertx
     * @return
     */
    private  static  JWTAuth init(Vertx vertx){
        String publicKey = null;
        String privateKey = null;
        //try {
            System.out.println("init");
            //publicKey = CryptoHelper.publicKey();
            publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3c/ZHHFYxLs0+HIfSYew\n" +
                    "mNBUY+EgqgarSZIj89U6wykhZMF4vYMHj7aOsfaMVaXk302LUluBOdtsDADJ62Ni\n" +
                    "ntb/b4Nr8bBWoKb7yDKvQZhgXKLlvKHuInUW+Blr2pXBwit0ltTBQEmLL1oTof5Y\n" +
                    "09wKTpRO/bZpSJl+dyUe0YE0FL9XRv3aVOu3XNd6z0atfJftiRR9WIvCy+Zclv3c\n" +
                    "6JIfabbsfbViwUs5IrYjqOFK/H6UsKO9rGTJX7nnZwH6SnNBea355+87bSrF56Sf\n" +
                    "YSOL2ueMpULQh8hhEKXZcccifdPITWMloeon2jNgZ0QUFWbtv9LQWh5dg/Bwz98D\n" +
                    "BwIDAQAB\n" +
                    "-----END PUBLIC KEY-----\n";
            //privateKey = CryptoHelper.privateKey();
            privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                    "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDdz9kccVjEuzT4\n" +
                    "ch9Jh7CY0FRj4SCqBqtJkiPz1TrDKSFkwXi9gwePto6x9oxVpeTfTYtSW4E522wM\n" +
                    "AMnrY2Ke1v9vg2vxsFagpvvIMq9BmGBcouW8oe4idRb4GWvalcHCK3SW1MFASYsv\n" +
                    "WhOh/ljT3ApOlE79tmlImX53JR7RgTQUv1dG/dpU67dc13rPRq18l+2JFH1Yi8LL\n" +
                    "5lyW/dzokh9ptux9tWLBSzkitiOo4Ur8fpSwo72sZMlfuednAfpKc0F5rfnn7ztt\n" +
                    "KsXnpJ9hI4va54ylQtCHyGEQpdlxxyJ908hNYyWh6ifaM2BnRBQVZu2/0tBaHl2D\n" +
                    "8HDP3wMHAgMBAAECggEAQ/U9UJCNoOC2bvQQD+gpu/gAEwXTiyQ18Hl82GTY9xgv\n" +
                    "6f24r19ovqgw+edOwaLtB1lruRTd98r5RIgtVRgDDCVU52Z9ePQM5nWiqIiW12MH\n" +
                    "CvrBplR6cEYtfl/TgonlbkcAFoIinz6pDR3s6/HQZ0CAS+j2dpcflKWqcHiIA6mf\n" +
                    "IXd215Xm5/2AVxT1Q3/6tlqI7fgk6eOGT6P8CJN6ttRiZQV9bWCa+PpVWe8NMXBx\n" +
                    "u7Ikhq2ethqSU789LUo3KuC2sr4/mzEhVXOYHK++eMHyng1u5OeHeOWLRpdf7i+f\n" +
                    "t2fOyRQlPSWio+OUne0E7hSftJ/B0tBuJfGTBK4e0QKBgQD2SmAAP8RVx5Bu8M2R\n" +
                    "OLq78OfSbXocA7EpwJfY2mKBmyv4Yfnh5rtU2dD59qEIf+uY87jYvZAnucr5EzTq\n" +
                    "H3sxoRH+i5dB/3N5+mEYVXXcPO0bvd8RpChx4+qdfJe0mpsnIv+MccSmQ5jq6qjR\n" +
                    "bt8D6hkhV6nxfBEeSNcqqgKYLwKBgQDmjm3Ew4O9arxxlUrxpxr3XjTw1hqUTdlI\n" +
                    "NZfuYV0pA4oDq5kofUXpAxbfM8cxAbILLY6DPczfC3wJqxBHt8Gua/Qkh3EsMSWg\n" +
                    "CXOCPoI05mwsQoQ0IzPwzy00bHWWe9BGYtRBwy3P6rsQECOmUjh6wT7Ov+jWEBdB\n" +
                    "hWG0h/E0qQKBgQDSzaoJzIqh2SQYsOdiayn5fyMNC0M/AzH6SKwvogw6XrRt7n5R\n" +
                    "CopBAAZMTPpcKhFfYwxOB0KBnhbKxdO+qJ9rRS//rT8T8C/IsbO1fkBY95UC76sk\n" +
                    "kFtF0AyaK3A11zcBcoVEkoP8w1L5j0xbpgggc5h632vIE6cbQsFX9RgvZQKBgQDe\n" +
                    "2Th8gcaqpaqz1uvzm+PSyjo9t5VFV+H3i+Xowi973zHdzlVh74RNb3ECGVglpE/l\n" +
                    "wgkIDgoiZUVlSvWXfKQ25Z28WTYo0vBdOgVuFOFiJH7WYQzMIGKqFnbJPzSBWfe8\n" +
                    "sPYdAn0bhPV+1zq+Hqb88CC3UoPJtQ/lgvknuGaK0QKBgQCkaujVyC07si08zR3U\n" +
                    "0kZ4AOUzbJ6GzD0RFAwDN32SKotLfc0bVHwEdchVWky5Pt8CjPXL1EGR9cpkkY5v\n" +
                    "tJCaOugcE96tughLOZBY5UL0un3hZ+O4TgWVCb6uCd1H2czfStu8BDOtjO6iHPqG\n" +
                    "3dY38nRlPfg1vWgum4Vl9mht4g==\n" +
                    "-----END PRIVATE KEY-----\n";

        publicKey = removeHeaderAndFooterFromKey(publicKey,"-----BEGIN PUBLIC KEY-----","-----END PUBLIC KEY-----");
        privateKey = removeHeaderAndFooterFromKey(privateKey,"-----BEGIN PRIVATE KEY-----","-----END PRIVATE KEY-----");
        jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setPublicKey(publicKey))
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        //.setBuffer(privateKey)));
                        .setSecretKey(privateKey)));
        return jwtAuth;
    }

    /**
     *
     * @param userEmail
     * @return
     */
    public static  String makeJwtToken(String userEmail) {
        JsonObject claims = new JsonObject()
                .put("userId", userEmail.substring(userEmail.lastIndexOf("-")+1));
        JWTOptions jwtOptions = new JWTOptions()
                .setAlgorithm("RS256")
                .setExpiresInMinutes(10_080) // 7 days
                .setIssuer("api-monitor")
                .setSubject(userEmail);
        System.out.println("makeJwtToken "+jwtAuth.toString());
        return jwtAuth.generateToken(claims, jwtOptions);
    }

    /**
     * remove the Header and the footer of the public and private keys
     * @param key
     * @param header
     * @param footer
     * @return
     */
    private static  String removeHeaderAndFooterFromKey(String key,String header, String footer){
        key = key.replace(header,"");
        key = key.replace(footer,"");
        return key;
    }

}
