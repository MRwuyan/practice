package com.roc;

import com.roc.api.IUserApi;
import com.roc.proxy.JDKProxyCreator;
import com.roc.proxy.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    @Bean
    ProxyCreator proxyCreator(){
        return new JDKProxyCreator();
    }
    @Bean
    FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator){
        return new FactoryBean<IUserApi>() {
            /**
             * 返回代理对象
             * @return
             * @throws Exception
             */
            @Override
            public IUserApi getObject() throws Exception {
                return(IUserApi) proxyCreator.createProxy(this.getObjectType());
            }

            /**
             *
             * @return
             */
            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
}
