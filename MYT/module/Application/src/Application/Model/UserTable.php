<?php

namespace Application\Model;

use Zend\Db\TableGateway\TableGateway;
use Zend\Db\Sql\Sql;
use Zend\Db\Sql\Select;
use Zend\Db\Adapter\Driver\ResultInterface;
use Zend\Db\ResultSet\ResultSet;
use Zend\Db\Sql\Expression;
use Zend\Db\Sql\Predicate\Between;
use \Zend\Db\Sql\Where;
use Application\Model\User;

class UserTable {

  protected $tableGateway;

  public function __construct(TableGateway $tableGateway) {
    $this->tableGateway = $tableGateway;
  }

  public function isRandomIdExists($random_id){
  	$where= new Where();
  	$where->equalTo('random_id', $random_id);
  	$rowset = $this->tableGateway->select($where);
  	$row = $rowset->current();
    if (empty($row)) {
    	return 0;
    } else {
    	return $row;
    }
  }
  
  public function insert($data) {
  	$this->tableGateway->insert($data);
  	return $lastId = $this->tableGateway->getLastInsertValue();
  }
}
