<?php
/**
 * Zend Framework (http://framework.zend.com/)
 *
 * @link      http://github.com/zendframework/ZendSkeletonApplication for the canonical source repository
 * @copyright Copyright (c) 2005-2015 Zend Technologies USA Inc. (http://www.zend.com)
 * @license   http://framework.zend.com/license/new-bsd New BSD License
 */

namespace Application\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;
use Application\Model\User;
use Application\Model\UserTable;


class IndexController extends AbstractActionController{

	protected $userTable;
	
	public function getUserTable(){
		if (!$this->userTable) {
			$sm = $this->getServiceLocator();
			$this->userTable = $sm->get('Application\Model\UserTable');
		}
		return $this->userTable;
	}
	
	
    public function indexAction(){
        return new ViewModel();
    }

    public function getUserIdAction() {
    	
    	$random_id = $_POST['random_id'];
    	
    	$isRandomIdExists = $this->getUserTable()->isRandomIdExists($random_id);
    	
    	if($isRandomIdExists != '0') {
    		$response['success'] = "Random Id is already present";
    		$response['user_id'] = $isRandomIdExists->user_id;
    		$response['status'] = TRUE;
    	}else{
    		
    		$user_id = $this->createUserId();
    		$data['random_id'] = $random_id;
    		$data['user_id'] = $user_id;
    		
    		$result = $this->getUserTable()->insert($data);
    		if($result) {
    			$response['success'] = 'Saved Successfully';
    			$response['user_id'] = $user_id;
    			$response['status'] = TRUE;
    		}else{
    			$response['success'] = 'Failure';
    			$response['status'] = TRUE;
    		}
    	}
    	return $this->getResponse()->setContent(json_encode($response));
    }
    
    public function createUserId() {
    	$random_digits = "";
    	for($i = 0; $i < 3; $i++) {
    		$random_digits .= mt_rand(0, 9);
    	}
    	$chars = array_merge(range('a', 'z'), range('a', 'z'));
    	shuffle($chars);
    	$random_char = implode(array_slice($chars, 0, 3));
    	return $random_digits.$random_char;
    }
}
