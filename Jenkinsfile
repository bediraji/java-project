pipeline {
	agent none
	
	stages {
		
		stage ('Unit Tests') {
			agent {
				label 'master'
			}
			steps {
			sh 'ant -f test.xml -v'
			junit 'reports/result.xml'
			}
		}
		
		stage('build'){
		agent {
				label 'master'
			}
			steps {
			sh 'ant -f build.xml -v'
			}
		post {
		success {
			archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
		
			}
		}
		}
		
		//stage('deploy') {
		//agent {
		//		label 'master'
		//	}
		//	steps {
		//	sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
		//	}
		//}
		
		stage("Running on CentOS") {
		agent {
				label 'master'
			}
		steps {
			//sh "wget http://rajibedi3.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
			sh "java -jar dist/rectangle_${env.BUILD_NUMBER}.jar 5 6"
			}
		}
		//stage("Test on Debian"){
		//	agent {
		//		docker 'openjdk:8u141-jre'
		//	}
		//	steps {
		//		sh "wget http://rajibedi3.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
		//		sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 5 6"
		//	}
		//} 
		
		stage ("Promote to Green")
		{
			steps {
				sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar dist/rectangle_${env.BUILD_NUMBER}_GREEN.jar"
			}
		}
	}
}