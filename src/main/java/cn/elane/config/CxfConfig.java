package cn.elane.config;

import cn.elane.EUDAP.EUDAPImpl.EUDAPServiceImpl;
import cn.elane.EUDAP.EUDAPMservice;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {
    //默认servlet路径/*,如果覆写则按照自己定义的来
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/web/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    //把实现类交给spring管理
    @Bean
    public EUDAPMservice appService() {
        return new EUDAPServiceImpl();
    }

    //终端路径
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), appService());
        endpoint.publish("/EUDAP");
        return endpoint;
    }
}
