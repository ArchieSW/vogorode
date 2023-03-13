minikube kubectl -- apply -f secret.yml
minikube kubectl -- apply -f local-storage.yml
minikube kubectl -- apply -f postgre-deployment.yml

minikube kubectl -- apply -f handyman-deployment.yml
minikube kubectl -- apply -f rancher-deployment.yml
minikube kubectl -- apply -f landscape-deployment.yml
