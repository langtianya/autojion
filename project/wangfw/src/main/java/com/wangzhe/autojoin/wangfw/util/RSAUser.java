package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import java.math.*;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.*;

public class RSAUser extends RSA
{

    public RSAUser()
    {

        pubModBytes = new BigInteger("9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247").
                toByteArray();

        priModBytes = new BigInteger("9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247").
                toByteArray();

        priPriExpBytes = new BigInteger("1886239156166242675657929005696116863800969728357619370861888926041477500595024535643547028972751834729515108233791728005492224507957110346500211730182737").
                toByteArray();

        try
        {
            pubKey = generateRSAPublicKey(pubModBytes, pubPubExpBytes);

            priKey = generateRSAPrivateKey(priModBytes, priPriExpBytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        catch (Error e)
        {
            e.printStackTrace();
        }
    }

}
