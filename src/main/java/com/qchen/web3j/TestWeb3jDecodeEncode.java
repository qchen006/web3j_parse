package com.qchen.web3j;

import org.jetbrains.annotations.TestOnly;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestWeb3jDecodeEncode {
    static Web3j web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/


    private static String fromAddr = "0x123";


    private static String paramAddr = "0xc5F0eE36780795dd7A7C2efF27057a37d7f82F5c";

    public static  void main(String[] args) throws IOException {
        List<Type> inputParameters = new ArrayList<>();
        Address address = new Address(paramAddr);
        inputParameters.add(address);

        Uint8 paramUnit = new Uint8(100);
        inputParameters.add(paramUnit);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function("testMethod", inputParameters, outputParameters);

        String encodedFunction = FunctionEncoder.encode(function);

        Transaction tran =  Transaction.createEthCallTransaction(
                fromAddr, "0x9c65ab58d8d978db963e63f2bfb7121627e3a739", encodedFunction);

        Request request =  web3j.ethCall(tran, DefaultBlockParameterName.LATEST);

        request.send();
    }

}
