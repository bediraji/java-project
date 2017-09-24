pipeline {
	agent none

	environment {
		MAJOR_VERSION = 1
	}

	stages {

		stage('Say Hello')
		{
			agent any
			steps {
				sayhello 'Hello Raji Bedi!'
			}
		}
		stage('Got information')
		{
			agent any
			steps {
				echo "My branch Name: ${env.BRANCH_NAME}"
				script {
					def myLib = new linuxacademy.git.gitStuff(); 
					echo "My Commit: ${myLib.gitCommit("${env.WORKSPACE}/.git")}"
				}
			}
		}
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
		//	sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
		//	}
		//}

		stage("Running on CentOS") {
		agent {
				label 'master'
			}
		steps {
			//sh "wget http://rajibedi3.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
			sh "java -jar dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 5 6"
			}
		}
		//stage("Test on Debian"){
		//	agent {
		//		docker 'openjdk:8u141-jre'
		//	}
		//	steps {
		//		sh "wget http://rajibedi3.mylabserver.com/rectangles/all/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
		//		sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 5 6"
		//	}
		//}

		stage ("Promote to Green") {
			agent {
				label 'master'
			}
			when {
				branch 'master'
			}
			steps {
				sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar dist/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}_GREEN.jar"
			}
		}
		stage ("Promote to Master branch") {
			agent {
				label 'master'
			}
			when {
				branch 'development'
			}
			steps {
				echo "Stashing any local changes"
				sh "git stash"
				echo "Checking out development branch"
				sh "git checkout development"
				echo "Checking out master branch"
				sh "git pull origin"
				sh "git checkout master"
				echo "Merging development into master"
				sh "git merge development"
				echo "Pushing to origin master"
				sh "git push origin master"
				echo "Tagging the release"
				sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
				sh "git push origin rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
			}
			post {
			success {
				emailext (
					subject: "${env.JOB_NAME} ${BUILD_NUMBER} promoted to master",
					body: "Console output <a href = '${env.BUILD_URL}'>${env.JOB_NAME} ${BUILD_NUMBER}</a>",
					to: "rajibedi@hotmail.com"
				)
				}
			}
		}
	}
	post {
		failure {
			emailext (
				subject: "${env.JOB_NAME} ${BUILD_NUMBER} failed",
				body: "Console output <a href = '${env.BUILD_URL}'>${env.JOB_NAME} ${BUILD_NUMBER}</a>",
				to: "rajibedi@hotmail.com"
			)
		}
	}
}
