DROP TABLE IF EXISTS task_evaluation_cf;
DROP TABLE IF EXISTS information_complementaire;
DROP TABLE IF EXISTS evaluation_criteria_value;
DROP TABLE IF EXISTS bourse_information_complementaire;
DROP TABLE IF EXISTS evaluation_synthesis_criteria;
DROP TABLE IF EXISTS evaluation_synthesis_criteria_value;
DROP TABLE IF EXISTS task_evaluation_synthesis;
DROP TABLE IF EXISTS task_evaluation_synthesis_value;
DROP TABLE IF EXISTS task_evaluation_evaluation;


/*==============================================================*/
/* Table structure for table task_evaluation_config			*/
/*==============================================================*/

CREATE TABLE task_evaluation_cf(
  id_task INT DEFAULT NULL,
  title_task VARCHAR(255) DEFAULT NULL,
  description_task VARCHAR(255) DEFAULT NULL,
  summary_title VARCHAR(255) DEFAULT NULL, 
  is_mandatory_summary SMALLINT DEFAULT 0,
  title_final_note VARCHAR(255) DEFAULT NULL,
  is_mandatory_final_note SMALLINT DEFAULT 0,
  is_final_note_automatic SMALLINT DEFAULT 0,
  best_score_final_note VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY  (id_task)
  );
/*==============================================================*/
/* Table structure for table task_evaluation_criteria		*/
/*==============================================================*/

CREATE TABLE task_evaluation_criteria (
  	id_criteria INT DEFAULT NULL,
	id_task INT DEFAULT NULL,
	title VARCHAR(255) DEFAULT NULL,
   	is_mandatory SMALLINT DEFAULT 0,
 	best_score VARCHAR(255) DEFAULT NULL,    
	criteria_position INT DEFAULT NULL,
	PRIMARY KEY  (id_criteria)
  );



/*==============================================================*/
/* Table structure for table task_evaluation_evaluation				*/
/*==============================================================*/

CREATE TABLE task_evaluation_evaluation (
  id_history INT NOT NULL DEFAULT 0,
  id_task INT DEFAULT NULL,
  summary LONG VARCHAR  DEFAULT NULL,
  final_note	VARCHAR(255),
  PRIMARY KEY  ( id_history,id_task)
  );




 

/*==============================================================*/
/* Table structure for table evaluation_criteria_value				*/
/*==============================================================*/

CREATE TABLE evaluation_criteria_value (
  id_history INT NOT NULL DEFAULT 0,
  id_task INT DEFAULT NULL,
  id_criteria INT DEFAULT NULL, 
  criteria_value	VARCHAR(255),
  PRIMARY KEY  ( id_history,id_task,id_criteria)
  );





 



/*==============================================================*/
/* Table structure for table bourse_information_complementaire                 			*/
/*==============================================================*/

  CREATE TABLE information_complementaire (
  id_dossier INT NOT NULL DEFAULT 0,
  rapporteur VARCHAR(255) DEFAULT NULL,
  note VARCHAR(50),
  type_ressource VARCHAR(200) NOT NULL,
  id INT NOT NULL DEFAULT 0,
  PRIMARY KEY  ( id_dossier,type_ressource,id)
  
  );
  
/*==============================================================*/
/* Table structure for table evaluation_synthesis_criteria  	*/
/*==============================================================*/
CREATE TABLE evaluation_synthesis_criteria (
  id_synthesis INT NOT NULL,
  id_history INT NOT NULL DEFAULT 0,
  id_task INT DEFAULT NULL,
  id_criteria INT DEFAULT NULL,
  PRIMARY KEY  ( id_synthesis )
);

/*==============================================================*/
/* Table structure for table evaluation_synthesis_criteria_value 	*/
/*==============================================================*/
CREATE TABLE evaluation_synthesis_criteria_value (
  id_synthesis INT NOT NULL,
  criteria_value VARCHAR(255)
);

-- index for performances
CREATE INDEX IDX_EVALUATION_SYNTHESIS_CRITERIA_VALUE ON evaluation_synthesis_criteria_value(id_synthesis);

/*==============================================================*/
/* Table structure for table task_evaluation_synthesis			*/
/*==============================================================*/

CREATE TABLE task_evaluation_synthesis (
  	id_criteria INT DEFAULT NULL,
	id_task INT DEFAULT NULL,
	title VARCHAR(255) DEFAULT NULL,
   	is_mandatory SMALLINT DEFAULT 0,
	criteria_position INT DEFAULT NULL,
	criteria_type VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY  (id_criteria)
);

/*==============================================================*/
/* Table structure for table task_evaluation_value				*/
/*==============================================================*/
CREATE TABLE task_evaluation_synthesis_value (
	id_value INT NOT NULL,
  	id_criteria INT DEFAULT NULL,
	criteria_value VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY  (id_value)
);

  