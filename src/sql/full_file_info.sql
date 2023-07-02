select fs.file_name, fs.file_path, f.name, d.name, tow.name, ft.term from files as fs
                                                                              join file_tags ft on fs.id = ft.file_id
                                                                              join discipline d on d.id = ft.discipline_id
                                                                              join faculty f on ft.faculty_id = f.id
                                                                              join type_of_work tow on tow.id = ft.type_id