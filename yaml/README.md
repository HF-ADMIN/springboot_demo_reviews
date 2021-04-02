# demo_tinfo yaml파일 사용 가이드

### 'demo-tinfo' namespace가 미리 생성되어 있어야 한다.
1. volume 관련 Object 생성
	1) reviews-storageclass 생성
		- provisioner는 'microk8s.io/hostpath'로 설정하여 storageclass를 생성합니다.
	2) reviews-pv 생성
		- 대상 storageclass는 'reviews-storage'로 설정합니다.
		- hostPath 타입으로 path는 '/mnt/demo-tinfo/reviews'로 설정합니다.
	3) reviews-pvc 생성
		- 대상 storageclass는 'reviews-storage'로 설정합니다.
		- pvc가 생성되면 대상 pv와 bound 되었는지 확인합니다.

2. deployment 생성
	1) reviews-deploy 생성
		- seoul timezone 설정합니다.
		- 위에서 생성한 pvc에 대한 설정합니다.
		- spec.template.spec.containers.image에 사용하는 도커계정을 입력합니다.(이 부분은 사용자가 변경 필요)
		
	
3. service 생성
	1) reviews-svc 생성
		- NodePort 타입으로 서비스를 생성합니다.
		- 서비스의 nodePort는 '30140'로 설정합니다.
