package com.riwi.industry.configuration.seeder;

import com.riwi.industry.domain.models.Load;
import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.domain.models.User;
import com.riwi.industry.domain.persistence.LoadRepository;
import com.riwi.industry.domain.persistence.PalletRepository;
import com.riwi.industry.domain.persistence.UserRepository;
import com.riwi.industry.utils.enums.Role;
import com.riwi.industry.utils.enums.StatusLoad;
import com.riwi.industry.utils.enums.StatusPallet;
import com.riwi.industry.utils.enums.TypePallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PalletRepository palletRepository;

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo se ejecuta si la tabla de usuarios está vacía
        if (userRepository.count() == 0) {
            for (int i = 1; i <= 5; i++) {
                User admin = User.builder()
                        .username("admin" + i)
                        .name("Admin")
                        .lastname("User" + i)
                        .email("admin" + i + "@example.com")
                        .password(passwordEncoder.encode("password" + i))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
            }

            for (int i = 1; i <= 5; i++) {
                User carrier = User.builder()
                        .username("carrier" + i)
                        .name("Carrier")
                        .lastname("User" + i)
                        .email("carrier" + i + "@example.com")
                        .password(passwordEncoder.encode("password" + i))
                        .role(Role.CARRIER)
                        .build();
                userRepository.save(carrier);
            }
        }

        // Solo se ejecuta si la tabla de pallets está vacía
        if (palletRepository.count() == 0) {
            List<Pallet> pallets = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Pallet pallet = Pallet.builder()
                        .status(StatusPallet.AVAILABLE)
                        .code("PalletCode" + i)
                        .ubication("Location" + i)
                        .type(TypePallet.METAL)
                        .maxCapacity(100L)
                        .build();
                pallets.add(pallet);
            }
            palletRepository.saveAll(pallets);
        }

        // Solo se ejecuta si la tabla de cargas está vacía
        if (loadRepository.count() == 0) {
            List<Load> loads = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Load load = Load.builder()
                        .code("LoadCode" + i)
                        .weight(100L + i)
                        .dimensions("10x10x" + (i * 10))
                        .status(StatusLoad.PENDING)
                        .build();
                loads.add(load);
            }
            loadRepository.saveAll(loads);
        }
    }
}
