# -*- mode: ruby -*-
# vi: set ft=ruby :


Vagrant.configure("2") do |config|
  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # config.vm.network "forwarded_port", guest: 54321, host: 54321

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"

  config.vm.provider "virtualbox" do |vb|
    # Display the VirtualBox GUI when booting the machine
    # vb.gui = true
    # vb.customize ["modifyvm", :id, "--vram", "256"]

    # Customize the amount of memory on the VM:
    vb.memory = "1024"
    vb.cpus = "1"
  end

  # code run on each instance
  config.vm.provision "shell", path: "vagrantfiles/setup_os_and_tools.sh"
  config.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBaseCommons", "https://github.com/OpenFogStack/FBaseCommons"]

  # startup instances
  config.vm.define "namingService" do |ns|
      ns.vm.box = "ubuntu/trusty64"
      ns.vm.network "private_network", ip: "192.168.0.200", bridge: "en0: WLAN (AirPort)"

      ns.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBaseNamingService", "https://github.com/OpenFogStack/FBaseNamingService.git"]
      ns.vm.provision "startup_jar", type: "shell", run: "always", path: "vagrantfiles/startup_jar.sh", args:["/home/vagrant/FBaseNamingService/target/fbasenamingservice-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "/vagrant/fbase_configurations/naming_service/namingService.properties", "/vagrant/logs/namingService.log"]
  end

  config.vm.define "raid" do |raid|
        raid.vm.box = "ubuntu/trusty64"
        raid.vm.network "private_network", ip: "192.168.0.100", bridge: "en0: WLAN (AirPort)"

        raid.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBase", "https://github.com/OpenFogStack/FBase"]
        raid.vm.provision "build_local_repo", type: "shell", path: "vagrantfiles/build_local_repo.sh", args:["/vagrant", "FBaseExampleClients"]
        raid.vm.provision "startup_jar", type: "shell", run: "always", path: "vagrantfiles/startup_jar.sh", args:["/home/vagrant/FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "/vagrant/fbase_configurations/node/raid.properties", "/vagrant/logs/raid.log"]
        # register other nodes at naming service (can do this safely on each startup)
        config.vm.provision "shell", inline: "echo Sleeping 10seconds to let the raid node startup."
        config.vm.provision "shell", inline: "sleep 10s"
        raid.vm.provision "run_client_registration", type: "shell", run: "always", path: "vagrantfiles/run_client.sh", args:["Registration.jar", "192.168.0.100", "8099", "/vagrant/fbase_configurations/node/floor0.json /vagrant/fbase_configurations/node/floor1.json /vagrant/fbase_configurations/node/floor2.json"]
        # create keygroups and add replica and trigger nodes
        raid.vm.provision "run_client_keygroupBootstrap", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["KeygroupBootstrap.jar", "192.168.0.100", "8099", "/vagrant/fbase_configurations/keygroup/garage_g1_floor0.json /vagrant/fbase_configurations/keygroup/garage_g1_floor1.json /vagrant/fbase_configurations/keygroup/garage_g1_floor2.json"]
        # update subscriptions
        raid.vm.provision "run_client_keygroupUpdateSubscriptions", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["KeygroupUpdateSubscriptions.jar", "192.168.0.100", "8099", "garage/g1/floor0 garage/g1/floor1 garage/g1/floor2"]
  end

  config.vm.define "floor0", autostart: true do |floor0|
        floor0.vm.box = "ubuntu/trusty64"
        floor0.vm.network "private_network", ip: "192.168.0.50", bridge: "en0: WLAN (AirPort)"

        floor0.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBase", "https://github.com/OpenFogStack/FBase"]
        floor0.vm.provision "startup_jar", type: "shell", run: "always", path: "vagrantfiles/startup_jar.sh", args:["/home/vagrant/FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "/vagrant/fbase_configurations/node/floor0.properties", "/vagrant/logs/floor0.log"]
        # update subscriptions
        floor0.vm.provision "run_client_keygroupUpdateSubscriptions", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["KeygroupUpdateSubscriptions.jar", "192.168.0.50", "8000", "garage/g1/floor0 garage/g1/floor1 garage/g1/floor2"]
        # start random value generation
        floor0.vm.provision "run_client_addRecords", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["AddRecordsClient.jar", "192.168.0.50", "8000", "garage/g1/floor0"]
  end

  config.vm.define "floor1", autostart: true do |floor1|
        floor1.vm.box = "ubuntu/trusty64"
        floor1.vm.network "private_network", ip: "192.168.0.51", bridge: "en0: WLAN (AirPort)"

        floor1.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBase", "https://github.com/OpenFogStack/FBase"]
        floor1.vm.provision "startup_jar", type: "shell", run: "always", path: "vagrantfiles/startup_jar.sh", args:["/home/vagrant/FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "/vagrant/fbase_configurations/node/floor1.properties", "/vagrant/logs/floor1.log"]
        # update subscriptions
        floor1.vm.provision "run_client_keygroupUpdateSubscriptions", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["KeygroupUpdateSubscriptions.jar", "192.168.0.51", "8001", "garage/g1/floor0 garage/g1/floor1 garage/g1/floor2"]
        # start random value generation
        floor1.vm.provision "run_client_addRecords", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["AddRecordsClient.jar", "192.168.0.51", "8001", "garage/g1/floor1"]
  end

  config.vm.define "floor2", autostart: true do |floor2|
        floor2.vm.box = "ubuntu/trusty64"
        floor2.vm.network "private_network", ip: "192.168.0.52", bridge: "en0: WLAN (AirPort)"

        floor2.vm.provision "shell", path: "vagrantfiles/build_github_repo.sh", args: ["FBase", "https://github.com/OpenFogStack/FBase"]
        floor2.vm.provision "startup_jar", type: "shell", run: "always", path: "vagrantfiles/startup_jar.sh", args:["/home/vagrant/FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "/vagrant/fbase_configurations/node/floor2.properties", "/vagrant/logs/floor2.log"]
        # update subscriptions
        floor2.vm.provision "run_client_keygroupUpdateSubscriptions", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["KeygroupUpdateSubscriptions.jar", "192.168.0.52", "8002", "garage/g1/floor0 garage/g1/floor1 garage/g1/floor2"]
        # start random value generation
        floor2.vm.provision "run_client_addRecords", type: "shell", run: "never", path: "vagrantfiles/run_client.sh", args:["AddRecordsClient.jar", "192.168.0.52", "8002", "garage/g1/floor2"]
  end

end
