import React, {Component} from 'react';

export default class Products extends React.Component {

    constructor(props) {
      super(props);
      
      //  this.state.products = [];
      this.state = {};
      this.state.filterText = "";
      var data = this.convert(this.props.data)
      this.state.products = data;
      console.log(this.state.products)
    }
    handleUserInput(filterText) {
      this.setState({filterText: filterText});
    };
    handleRowDel(product) {
      var index = this.state.products.indexOf(product);
      this.state.products.splice(index, 1);
      this.setState(this.state.products);
      console.log(this.state.products)
    };
    convert(data) {
      var products = []
      for(var product in data){
        products[product].id = product
        products[product].Zjazd_nr = data[product][0]
        products[product].Data = data[product][1]
        products[product].Godziny = data[product][2]
        products[product].G1_przedmiot = data[product][3]
        products[product].G1_sala = data[product][4]
        products[product].G1_prowadzacy = data[product][5]
        products[product].G2_przedmiot = data[product][6]
        products[product].G2_sala = data[product][7]
        products[product].G2_prowadzacy = data[product][8]
      }
      return products
    }
  
    handleAddEvent(evt) {
      var id = (+ new Date() + Math.floor(Math.random() * 999999)).toString(36);
      var product = {
        id: id,
        Zjazd_nr: "",
        Data: "",
        Godziny: "",
        G1_prowadzacy: "",
        G1_sala: "",
        G1_przedmiot: "",
        G2_prowadzacy: "",
        G2_sala: "",
        G2_przedmiot: "",
      }
      this.state.products.push(product);
      this.setState(this.state.products);
  
    }
  
    handleProductTable(evt) {
      var item = {
        id: evt.target.id,
        name: evt.target.name,
        value: evt.target.value
      };
  var products = this.state.products.slice();
    var newProducts = products.map(function(product) {
  
      for (var key in product) {
        if (key == item.name && product.id == item.id) {
          product[key] = item.value;
  
        }
      }
      return product;
    });
      this.setState({products:newProducts});
    //  console.log(this.state.products);
    };
    render() {
  
      return (
        <div>
          <SearchBar filterText={this.state.filterText} onUserInput={this.handleUserInput.bind(this)}/>
          <ProductTable onProductTableUpdate={this.handleProductTable.bind(this)} onRowAdd={this.handleAddEvent.bind(this)} onRowDel={this.handleRowDel.bind(this)} products={this.state.products} filterText={this.state.filterText}/>
        </div>
      );
  
    }
  
  }
  class SearchBar extends React.Component {
    handleChange() {
      this.props.onUserInput(this.refs.filterTextInput.value);
    }
    render() {
      return (
        <div>
  
         
  
        </div>
  
      );
    }
  
  }
  
  class ProductTable extends React.Component {
  
    render() {
      var onProductTableUpdate = this.props.onProductTableUpdate;
      var rowDel = this.props.onRowDel;
      var filterText = this.props.filterText;
      var id = 0;
      var product = this.props.products.map(function(product) {
        // if (product.name.indexOf(filterText) === -1) {
        //   return;
        // }
      //   if (product[3] === filterText) {
      //     return;
      //  }
        product.id = id;
        id=id+1
        return (<ProductRow onProductTableUpdate={onProductTableUpdate} product={product} onDelEvent={rowDel.bind(this)} key={product.id}/>)
      });
      return (
        <div>
  
  
        <button type="button" onClick={this.props.onRowAdd} className="btn btn-success pull-right">Add</button>
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Zjazd nr</th>
                <th>Data</th>
                <th>Godziny</th>
                <th>Grupa 1 - Przedmiot</th>
                <th>Sala</th>
                <th>Grupa 1 - Prowadzacy</th>
                <th>Grupa 2 - Przedmiot</th>
                <th>Sala</th>
                <th>Grupa 2 - Prowadzacy</th>
              </tr>
            </thead>
  
            <tbody>
              {product}
  
            </tbody>
  
          </table>
        </div>
      );
  
    }
  
  }
  
  class ProductRow extends React.Component {
    onDelEvent() {
      this.props.onDelEvent(this.props.product);
  
    }
    render() {
  
      return (
        <tr className="eachRow">
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            "type": "Zjazd_nr",
            value: this.props.product[0],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "Data",
            value: this.props.product[1],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "Godziny",
            value: this.props.product[2],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G1_przedmiot",
            value: this.props.product[3],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G1_sala",
            value: this.props.product[4],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G1_prowadzacy",
            value: this.props.product[5],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G2_przedmiot",
            value: this.props.product[6],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G2_sala",
            value: this.props.product[7],
            id: this.props.product.id
          }}/>
          <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
            type: "G2_prowadzacy",
            value: this.props.product[8],
            id: this.props.product.id
          }}/>
          <td className="del-cell">
            <input type="button" onClick={this.onDelEvent.bind(this)} value="X" className="del-btn"/>
          </td>
        </tr>
      );
  
    }
  
  }
  class EditableCell extends React.Component {
  
    render() {
      return (
        <td>
          <input type='text' name={this.props.cellData.type} id={this.props.cellData.id} value={this.props.cellData.value} onChange={this.props.onProductTableUpdate}/>
        </td>
      );
  
    }
  
  }
  //ReactDOM.render( < Products / > , document.getElementById('container'));
  