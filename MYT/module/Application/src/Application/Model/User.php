<?php
namespace Application\Model;

use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class User {

	public $user_id;
	public $random_id;
	
	public function exchangeArray($data) {
		$this->user_id     = (!empty($data['user_id'])) ? $data['user_id'] : null;
		$this->random_id     = (!empty($data['random_id'])) ? $data['random_id'] : null;
	}

	public function getArrayCopy() {
		return get_object_vars($this);
	}
}
