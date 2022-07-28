pipeline {
    agent any
    stages {
        stage ('Checkout Project') {
            steps {
                checkout scm
                sh "echo ' ' > /root/.ssh/known_hosts"
            }
        }
  
        stage ('Show Values') {
            steps {
                sh "echo Test case to run: "
                sh "echo Inventory: $inventory"
                sh "echo level: $level_name"
                sh "echo level Nexus Name: $level_complement"
                sh "echo opcion: $opc"
            }
        }

        stage ('Run_Playbook Import_Inventory') {
            when {
                beforeAgent true
                expression { inventory == 'Upload_inventory' }
            }
            steps {
                sh "echo '$import_inventory' > Inventories/import_inventory.yml"
                sh "ansible-playbook playbooks/os4690/Install_Controller.yml -vv  -i Inventories/import_inventory.yml -e 'level_complement=$level_complement opc=$opc level_name=$level_name'"
            }
        }
        
        stage ('Post playbook') {
            steps {
                sh "echo Test case to run: "
                sh "echo Done!"
            }
        }
    }
    post { 
        always { 
            cleanWs()
        }
    }
}
