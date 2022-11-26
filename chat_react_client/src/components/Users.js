import React from 'react';
import Button from '@mui/material/Button';  

class Users extends React.Component {

    constructor() {
        super();
    }
    render() {
        return (
            <div className="user_list">
                {/*
                    //this.props.users
                    this.props.users.map((user) => {
                        console.log(user.userName);
                        return (
                            <div>
                            <Button variant="text" value={user.userName} onClick={(e) => {console.log(e.target.value); /*this.props.selectUser(e.target.value) }}>{user.userName}</Button> 
                            <br></br>
                            </div>
                        );
                    })
                */}
            </div>
        );
    }
}

/*UserList.propTypes = {
    users: React.PropTypes.array.isRequired
};*/

export default Users;