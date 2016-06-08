<?php
namespace Application\Model;

use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class UserDetails {

	public $user_id;
	public $name;
	public $address;
	public $email;
	public $phone;
	
	public function exchangeArray($data) {
		$this->user_id     = (!empty($data['user_id'])) ? $data['user_id'] : null;
		$this->name     = (!empty($data['name'])) ? $data['name'] : null;
		$this->address     = (!empty($data['address'])) ? $data['address'] : null;
		$this->email     = (!empty($data['email'])) ? $data['email'] : null;
		$this->phone     = (!empty($data['phone'])) ? $data['phone'] : null;
	}

	public function getArrayCopy() {
		return get_object_vars($this);
	}
}
