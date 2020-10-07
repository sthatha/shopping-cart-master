pipeline {

    agent any

    tools {
        maven 'mvn-v3.6.0'
        jdk 'jdk8'
    }

    environment {
//         DOCKER_CREDENTIALS = credentials('docker-registry-credentials')
        loginServer = "registry.hub.docker.com/rangasdocker"
        imageWithTag = "$loginServer/demo:latest:latest"
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building......'
                sh 'mvn clean package -DskipTests=True'
            }
        }

        stage ('Docker Build') {
            steps {
                sh 'pwd'
                sh 'ls -lrt'
                echo 'Start - Push image to ECR'

//                 withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'fego-eks-user-aws']]) {

                    sh '''#!/bin/sh
//                         DOCKER_command=$(aws ecr get-login --region ap-southeast-1) > /dev/null
//
//                         // Output of the above command would be similar to this:
//                         // docker login -u AWS -p PASSWORD -e none https://161342106387.dkr.ecr.ap-southeast-1.amazonaws.com
//
//                         // Extracting only the password
//                         docker login -u AWS -p $(echo $DOCKER_command | sed -E 's/.*-p//' | sed -E 's/-e.*//' | xargs) 161342106387.dkr.ecr.ap-southeast-1.amazonaws.com
//
                        docker login registry.hub.docker.com -u rangasdocker -p Testing123!
                    '''
                    script {
                        def image = docker.build imageWithTag
                        image.push()
                        echo 'Done pushing the image.'
                    }
//                 }

            }
        }

        stage('deploy') {
            steps{
                withCredentials([kubeconfigFile(credentialsId: 'eks-kubeconfig-fego-dev', variable: 'KUBECONFIG')]){
                    sh '''
                        export KUBECONFIG=$KUBECONFIG
                        kubectl apply -f cicd/kubernetes/configMap.yaml
                        kubectl apply -f cicd/kubernetes/service.yaml
                        kubectl apply -f cicd/kubernetes/deployment.yaml
                    '''
                }
            }
        }

    }

}
