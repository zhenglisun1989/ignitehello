package com.zhengli.ignite;

import com.zhengli.ignite.entity.FactBalance;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;


/**
 * Created by sunzhengli on 2020/7/12
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class IgniteHello {
    private static final Logger LOG = LoggerFactory.getLogger(IgniteHello.class);

    public static void main(String[] args) {
        SpringApplication.run(IgniteHello.class, args);
    }

    @Bean
    public IgniteConfigurer configurer() {
        return cfg -> {

            TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
            tcpDiscoverySpi.setIpFinder(new TcpDiscoveryMulticastIpFinder());
            cfg.setDiscoverySpi(tcpDiscoverySpi);
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Autowired
            Ignite ignite;

            @Override
            public void run(String... args) throws Exception {
                CacheConfiguration<Long, FactBalance> balanceCacheConfiguration = new CacheConfiguration<>();
                balanceCacheConfiguration.setName("FactBalancesql");
                balanceCacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
                //balanceCacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
                balanceCacheConfiguration.setIndexedTypes(Long.class, FactBalance.class);
                ignite.destroyCache("FactBalancesql");
                IgniteCache<Long, FactBalance> balanceIgniteCache = ignite.getOrCreateCache(balanceCacheConfiguration);

                FactBalance factBalance = new FactBalance();
                factBalance.setIdentifier(1l);
                factBalance.setCalendarId(20200710);
                factBalance.setPfId(12345);
                factBalance.setLocalCurrencyId(132);
                factBalance.setProductProcessorId(1);
                factBalance.setEdLocalBalance(new BigDecimal(3));
                factBalance.setSdLocalBalance(new BigDecimal(4));
                factBalance.setTdLocalBalance(new BigDecimal(5));

                balanceIgniteCache.put(1l, factBalance);

                FactBalance factBalance1 = balanceIgniteCache.get(1l);
                LOG.info(factBalance1.toString());
                System.out.println(factBalance1);

                String sql = "select * from FactBalance where calendarId = ?";

                Iterable<?> col = balanceIgniteCache.query(new SqlFieldsQuery(sql).setArgs(20200710)).getAll();

                for (Object o : col) {
                    LOG.info(">>>>>>>" + o.toString());
                }

            }

        };
    }



}
