minikube kubectl -- apply -f postgre-deployment.yml
minikube kubectl -- apply -f mongo-deployment.yml

sleep 10s

minikube kubectl -- apply -f handyman-deployment.yml
minikube kubectl -- apply -f rancher-deployment.yml
minikube kubectl -- apply -f landscape-deployment.yml
